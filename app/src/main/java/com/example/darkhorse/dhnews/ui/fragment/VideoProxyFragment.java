package com.example.darkhorse.dhnews.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darkhorse.preferencesmanager.PreferencesUtils;
import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseFragment;
import com.example.darkhorse.dhnews.ui.adapter.VideoListFragmentPagerAdapter;
import com.example.darkhorse.dhnews.ui.widgets.ViewPagerIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DarkHorse on 2017/12/19.
 */

public class VideoProxyFragment extends BaseFragment {
    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private List<VideoListFragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_proxy,container,false);
        findViewById(rootView);
        setListener(rootView);
        init();
        return rootView;
    }

    @Override
    protected void findViewById(View rootView) {
        mIndicator = rootView.findViewById(R.id.indicator_video);
        mViewPager = rootView.findViewById(R.id.vp_video);
    }

    @Override
    protected void setListener(View rootView) {
    }

    @Override
    protected void init() {
        initIndicator();
    }

    private void initIndicator() {
        String videoMap = (String) PreferencesUtils.get(mActivity, "videoMap", "");
        Gson gson = new Gson();
        Map<String, String> videos = gson.fromJson(videoMap, new TypeToken<Map<String, String>>() {
        }.getType());

        List<String> titles = new ArrayList<>();
        mFragments = new ArrayList<>();
        for (Map.Entry<String, String> entry : videos.entrySet()) {
            titles.add(entry.getKey());
            mFragments.add(new VideoListFragment(entry.getValue()));
        }
        mIndicator.setTabItemTtiles(titles);

        VideoListFragmentPagerAdapter adapter = new VideoListFragmentPagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);

        mIndicator.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

//    public void onBackPressed(){
//        mFragments.get(mViewPager.getCurrentItem()).onBackPressed();
//    }

}
