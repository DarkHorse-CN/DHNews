package com.example.darkhorse.dhnews.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.darkhorse.dhnews.ui.fragment.NewsListFragment;

import java.util.List;

/**
 * Created by DarkHorse on 2017/11/16.
 */

public class NewsListFragmenPagerAdapter extends FragmentPagerAdapter {

    private List<NewsListFragment> mFragments;

    public NewsListFragmenPagerAdapter(FragmentManager fm, List<NewsListFragment> fragments) {
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
