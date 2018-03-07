package com.example.darkhorse.dhnews.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseFragment;
import com.example.darkhorse.dhnews.bean.NewsBean;
import com.example.darkhorse.dhnews.mvp.presenter.NewsPresenter;
import com.example.darkhorse.dhnews.mvp.view.INewsView;
import com.example.darkhorse.dhnews.ui.activity.NewsDetailsActivity;
import com.example.pulltorefreshlayout.PullToRefreshLayout;
import com.example.pulltorefreshlayout.interfaces.IRefreshListener;
import com.example.rvadapter.ItemView;
import com.example.rvadapter.RvAdapter;
import com.example.rvadapter.bean.MenuBean;
import com.example.rvadapter.interfaces.IMulItemBind;
import com.example.rvadapter.interfaces.OnItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DarkHorse on 2017/11/16.
 */

public class NewsListFragment extends BaseFragment implements INewsView {

    private PullToRefreshLayout mPullToRefreshLayout;
    private AnimatedVectorDrawable mAnimatedVectorDrawable;
    private ImageView iv_header;
    private MyHandler mHandler;

    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private String mType;
    private List<NewsBean> mNewsList;

    private NewsPresenter mPresenter;

    private boolean isRefresh = false;

    public NewsListFragment() {
        this("");
    }

    @SuppressLint("ValidFragment")
    public NewsListFragment(String type) {
        mType = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        findViewById(rootView);
        setListener(rootView);
        init();
        return rootView;
    }

    @Override
    protected void findViewById(View rootView) {
        mPullToRefreshLayout = rootView.findViewById(R.id.pl_news);
        iv_header = rootView.findViewById(R.id.iv_header);
        mRecyclerView =  rootView.findViewById(R.id.rv_news);
    }

    @Override
    protected void setListener(View rootView) {
    }

    @Override
    protected void init() {
        mHandler = new MyHandler(mActivity);
        mPresenter = new NewsPresenter(mActivity, this);
        mNewsList = new ArrayList<>();
        initRecyclerView();
        initPullToRefreshLayout();
        mPresenter.refresh();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new RvAdapter<>(mNewsList, mRecyclerView)
                .setMulItemBind(new IMulItemBind<NewsBean>() {
                    @Override
                    public int setItemViewType(NewsBean newsBean, int i) {
                        if (newsBean.getThumbnail_pic_s03() != null) {
                            return 3;
                        }
                        return 1;
                    }

                    @Override
                    public int setLayoutId(int itemType) {
                        if (itemType == 3) {
                            return R.layout.item_news_recycleview_3;
                        }
                        return R.layout.item_news_recycleview_1;
                    }

                    @Override
                    public void onViewBind(ItemView itemView, NewsBean newsBean, int i) {
                        itemView.setText(R.id.tv_title, newsBean.getTitle());
                        itemView.setText(R.id.tv_author_name, newsBean.getAuthor_name());
                        itemView.setText(R.id.tv_date, newsBean.getDate());
                        Glide.with(mActivity).load(newsBean.getThumbnail_pic_s()).into((ImageView) itemView.getViewById(R.id.iv_pic_1));
                        if (newsBean.getThumbnail_pic_s03() != null) {
                            Glide.with(mActivity).load(newsBean.getThumbnail_pic_s02()).into((ImageView) itemView.getViewById(R.id.iv_pic_2));
                            Glide.with(mActivity).load(newsBean.getThumbnail_pic_s03()).into((ImageView) itemView.getViewById(R.id.iv_pic_3));
                        }
                    }

                    @Override
                    public MenuBean addLeftMenu(int i) {
                        return null;
                    }

                    @Override
                    public MenuBean addRightMenu(int i) {
                        return null;
                    }
                }).addOnItemClickListener(new int[]{R.id.news_item}, new OnItemClickListener() {
                    @Override
                    public void onItemClick(ItemView itemView, int i, int position) {
                        NewsBean bean = mNewsList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", bean.getUrl());
                        mActivity.startActivity(NewsDetailsActivity.class, bundle);
                    }

                });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mRecyclerView.canScrollVertically(1)) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mPresenter.loadMore(linearLayoutManager.findLastVisibleItemPosition());
                }
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
                    tv_header = (TextView) view.findViewById(R.id.tv_header);
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
                mHandler.sendEmptyMessage(123);
                if (tv_header == null) {
                    tv_header = (TextView) header.findViewById(R.id.tv_header);
                }
                showLoading();
                tv_header.setText("正在刷新");
                mPresenter.update();
            }
        });
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void refresh(List<NewsBean> list) {
        mNewsList.clear();
        if (list != null) {
            mNewsList.addAll(list);
        }
        mAdapter.notifyDataSetChanged();
        if (mAnimatedVectorDrawable != null && mAnimatedVectorDrawable.isRunning()) {
            isRefresh = false;
            mAnimatedVectorDrawable.stop();
        }
        mPullToRefreshLayout.initLayout();
    }

    @Override
    public void loadMore(List<NewsBean> list) {
        mNewsList.addAll(list);
        mAdapter.notifyDataSetChanged();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showLoading() {
        if (mAnimatedVectorDrawable == null) {
            mAnimatedVectorDrawable = (AnimatedVectorDrawable) iv_header.getDrawable();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAnimatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    if (isRefresh) {
                        mHandler.post(mVectorTask);
                    }
                }
            });
        }
        mHandler.post(mVectorTask);
        isRefresh = true;

    }

    private Runnable mVectorTask = new Runnable() {
        @Override
        public void run() {
            mAnimatedVectorDrawable.start();
        }
    };

    class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    break;
            }
            super.dispatchMessage(msg);
        }
    }
}
