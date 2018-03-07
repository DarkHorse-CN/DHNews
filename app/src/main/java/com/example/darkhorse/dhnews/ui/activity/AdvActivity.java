package com.example.darkhorse.dhnews.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.darkhorse.preferencesmanager.PreferencesUtils;
import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseActivity;
import com.example.darkhorse.dhnews.utils.constant.API;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AdvActivity extends BaseActivity implements View.OnClickListener {

    private int REQUEST_READ_PHONE_STATE = 1;

    private ImageView iv_adv_01;
    private TextView tv_skip;

    private Observable mTimer;
    private boolean isSkiped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv);
        findViewById();
        setListener();
        init();
        setFullScreen();
    }

    @Override
    protected void findViewById() {
        iv_adv_01 = findViewById(R.id.iv_adv_01);
        tv_skip = findViewById(R.id.tv_skip);
    }

    @Override
    protected void setListener() {
        tv_skip.setOnClickListener(this);
    }

    @Override
    protected void init() {
        Glide.with(mContext).load(R.mipmap.guide_page_1).into(iv_adv_01);

        final int count = 0;
        mTimer = Observable.interval(1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        mTimer.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                tv_skip.setText("跳过 " + aLong + "s");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (!isSkiped) {
                    startActivity(TabActivity.class);
                    finish();
                }
            }
        });

        getVideoTitle();
    }

    private void getVideoTitle() {
        Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                Document document = Jsoup.connect(API.MEIPAI_URL).get();
                e.onNext(document);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Document>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Document document) {
                        Element nav = document.getElementById("nav");
                        Element nav_1 = nav.child(0);

                        Map<String, String> map = new LinkedHashMap<>();
                        for (Element element : nav_1.children()) {
                            map.put(element.html(), element.attr("href"));
                        }
                        map.remove("首页");
                        map.remove("直播");
                        Gson gson = new Gson();
                        String videoMap = gson.toJson(map);
                        PreferencesUtils.put(mContext, "videoMap", videoMap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                isSkiped = true;
                startActivity(TabActivity.class);
                finish();
                break;
        }
    }
}
