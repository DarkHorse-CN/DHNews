package com.example.darkhorse.dhnews.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseFragment;
import com.example.darkhorse.dhnews.bean.VideoBean;
import com.example.darkhorse.dhnews.ui.activity.VideoActivity;
import com.example.darkhorse.dhnews.ui.widgets.CircleImageView;
import com.example.darkhorse.dhnews.utils.constant.API;
import com.example.pulltorefreshlayout.PullToRefreshLayout;
import com.example.pulltorefreshlayout.interfaces.IRefreshListener;
import com.example.rvadapter.ItemView;
import com.example.rvadapter.RvAdapter;
import com.example.rvadapter.interfaces.IItemBind;
import com.example.rvadapter.interfaces.OnItemClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DarkHorse on 2017/12/19.
 */

public class VideoListFragment extends BaseFragment {
    private String mHref;

    private PullToRefreshLayout mPullToRefreshLayout;
    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private List<VideoBean> mVideos;

    public VideoListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_list,container,false);
        findViewById(rootView);
        setListener(rootView);
        init();
        return rootView;
    }

    @SuppressLint("ValidFragment")
    public VideoListFragment(String href) {
        mHref = href;
    }

    @Override
    protected void findViewById(View rootView) {
        mPullToRefreshLayout = rootView.findViewById(R.id.pl_video);
        mRecyclerView = rootView.findViewById(R.id.rv_video);
    }

    @Override
    protected void setListener(View rootView) {
    }

    @Override
    protected void init() {
        mVideos = new ArrayList<>();
        initRecyclerView();
        initPullToRefreshLayout();
        refreshVideo();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        mAdapter = new RvAdapter<>(mVideos, mRecyclerView)
                .setItemBind(R.layout.item_video_recyclerview, new IItemBind() {
                    @Override
                    public void onViewBind(ItemView itemView, int i) {
                        VideoBean videoBean = mVideos.get(i);
                        ImageView iv_bg = (ImageView) itemView.getViewById(R.id.iv_bg);
                        final CircleImageView iv_author = (CircleImageView) itemView.getViewById(R.id.iv_author);
                        itemView.setText(R.id.tv_author,videoBean.getAuthor_name());
                         itemView.setText(R.id.tv_praise,videoBean.getPraise());

                        Glide.with(mActivity).load(videoBean.getImg()).into(iv_bg);
                        Glide.with(mActivity).load(videoBean.getAuthor_icon()).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                iv_author.setSrc(resource);
                            }
                        });
                    }

                }).addOnItemClickListener(new int[]{R.id.item_video}, new OnItemClickListener() {
                    @Override
                    public void onItemClick(ItemView itemView, int i, int i1) {
                        String videoUrl = API.MEIPAI_URL + "/media/" + mVideos.get(i1).getId();
                        String flvcdUrl = API.FLVCD_URL + videoUrl;
                        playVideo(flvcdUrl);
                    }
                });
    }

    private void playVideo(final String url) {
        Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                Document document = Jsoup.connect(url).get();
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
                        Elements elements = document.getElementsByClass("link");
                        String videoUrl = elements.get(0).attr("href");
                        Bundle bundle = new Bundle();
                        bundle.putString("videoUrl",videoUrl);
                        mActivity.startActivity(VideoActivity.class,bundle);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void initPullToRefreshLayout() {
        mPullToRefreshLayout.setRefreshListener(new IRefreshListener() {
            TextView tv_header;

            @Override
            public void init(View header) {
                if (header != null) {
                    if (tv_header == null) {
                        tv_header = (TextView) header.findViewById(R.id.tv_header);
                    }
                    tv_header.setText("下拉刷新");
                }

            }

            @Override
            public boolean prepare(View view, float v) {
                if (tv_header == null) {
                    tv_header = view.findViewById(R.id.tv_header);
                }

                if (v >= 100) {
                    tv_header.setText("松开刷新");
                    return true;
                } else {
                    init(view);
                    return false;
                }
            }

            @Override
            public void refresh(View header) {
                if (tv_header == null) {
                    tv_header = header.findViewById(R.id.tv_header);
                }
                tv_header.setText("正在刷新");
                refreshVideo();
            }
        });
    }

    private void refreshVideo() {
        Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                Document document = Jsoup.connect(API.MEIPAI_URL + mHref).get();
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
                        mVideos.clear();
                        Element mediasList = document.getElementById("mediasList");
                        for (Element element : mediasList.children()) {
                            Element imgElement = element.child(1);
                            Element idElement = element.child(2);
                            Element detailElement = element.child(3);

                            String img = imgElement.attr("src");
                            String id = idElement.attr("data-id");
                            String author_icon = detailElement.child(0).child(0).attr("src");
                            String author_name = detailElement.child(1).child(0).attr("title");
                            String praise;
                            if(detailElement.childNodeSize()==2){
                                praise = detailElement.child(2).child(1).child(1).attr("content");
                            }else{
                                praise = detailElement.child(2).child(1).child(0).attr("content");
                            }
                            if (!praise.isEmpty()) {
                                praise = praise.substring(0, praise.length() - 2);
                            }
                            VideoBean videoBean = new VideoBean(img, id, author_icon, author_name, praise);
                            mVideos.add(videoBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mAdapter.notifyDataSetChanged();
                        mPullToRefreshLayout.initLayout();
                    }
                });
    }

}
