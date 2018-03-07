package com.example.darkhorse.dhnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.darkhorse.dhnews.ui.fragment.VideoListFragment;

import java.util.List;

/**
 * Created by DarkHorse on 2017/12/19.
 */

public class VideoListFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<VideoListFragment> mFragments;

    public VideoListFragmentPagerAdapter(FragmentManager fm, List<VideoListFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
