package com.example.darkhorse.dhnews.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseFragment;
import com.example.darkhorse.dhnews.ui.adapter.NewsListFragmenPagerAdapter;
import com.example.darkhorse.dhnews.ui.widgets.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DarkHorse on 2017/11/21.
 */

public class NewsProxyFragment extends BaseFragment {

    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_proxy,container,false);
        findViewById(rootView);
        setListener(rootView);
        init();
        return rootView;
    }

    @Override
    protected void findViewById(View rootView) {
        mIndicator = rootView.findViewById(R.id.mIndicator);
        mViewPager = rootView.findViewById(R.id.mViewpager);
    }

    @Override
    protected void setListener(View rootView) {

    }

    @Override
    protected void init() {
        initViewPager();
        initIndicator();
    }

    private void initViewPager() {
        List<NewsListFragment> fragments = new ArrayList<>();
        String[] types = getResources().getStringArray(R.array.array_news_type);
        for (int i = 0; i < 8; i++) {
            NewsListFragment fragment = new NewsListFragment(types[i]);
            fragments.add(fragment);
        }
        NewsListFragmenPagerAdapter adapter = new NewsListFragmenPagerAdapter(mActivity.getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
    }

    private void initIndicator() {
        String[] titles = getResources().getStringArray(R.array.array_tab_title);
        mIndicator.setTabItemTtiles(Arrays.asList(titles));
        mIndicator.setViewPager(mViewPager);
    }
}
