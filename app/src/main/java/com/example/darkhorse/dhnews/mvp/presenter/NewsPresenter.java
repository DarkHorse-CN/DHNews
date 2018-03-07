package com.example.darkhorse.dhnews.mvp.presenter;

import android.content.Context;

import com.darkhorse.httpmanager.HttpManager;
import com.darkhorse.preferencesmanager.PreferencesUtils;
import com.example.darkhorse.dhnews.bean.NewsBean;
import com.example.darkhorse.dhnews.bean.NewsJson;
import com.example.darkhorse.dhnews.bean.NewsResult;
import com.example.darkhorse.dhnews.retrofit.NewsService;
import com.example.darkhorse.dhnews.mvp.model.NewsModel;
import com.example.darkhorse.dhnews.mvp.view.INewsView;
import com.example.darkhorse.dhnews.utils.DBManager;
import com.example.darkhorse.dhnews.utils.constant.API;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DarkHorse on 2017/11/16.
 */

public class NewsPresenter implements INewsPresenter {
    private Context mContext;
    private INewsView mView;
    private NewsModel mNewsModel;

    public NewsPresenter(Context context, INewsView view) {
        mContext = context;
        attachView(view);
        mNewsModel = new NewsModel();
    }

    @Override
    public void attachView(INewsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void update() {
        NewsService newsService =  HttpManager.getInstance().getService(NewsService.class);
        Observable<NewsJson> observable = newsService.getNewsJson(mView.getType(), API.NEWS_APPKEY);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsJson value) {
                        NewsResult result = value.getResult();
                        if (result != null) {
                            List<NewsBean> newList = result.getData();
                            List<NewsBean> oldList;
                            if ("top".equals(mView.getType())) {
                                oldList = DBManager.getInstance(mContext).queryNewsListAll();
                            } else {
                                oldList = DBManager.getInstance(mContext).queryNewsListByType((String) PreferencesUtils.get(mContext, mView.getType(), ""));
                                oldList.addAll(DBManager.getInstance(mContext).queryNewsListByType((String) PreferencesUtils.get(mContext, "top", "")));
                            }
                            mNewsModel.update(mContext, newList, oldList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        refresh();
                    }
                });

    }

    @Override
    public void refresh() {
        List<NewsBean> list = mNewsModel.refresh(mContext, (String) PreferencesUtils.get(mContext, mView.getType(), ""));
        mView.refresh(list);
    }

    @Override
    public void loadMore(int fromIndex) {
        List<NewsBean> list = mNewsModel.loadMore(mContext, (String) PreferencesUtils.get(mContext, mView.getType(), ""), fromIndex);
        mView.loadMore(list);
    }

}
