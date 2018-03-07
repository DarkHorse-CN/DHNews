package com.example.darkhorse.dhnews;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.darkhorse.dhnews.base.BaseActivity;
import com.example.darkhorse.dhnews.ui.fragment.NewsListFragment;


public class MainActivity extends BaseActivity {

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        mFrameLayout = (FrameLayout) findViewById(R.id.mFrameLayout);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new NewsListFragment();
        transaction.add(R.id.mFrameLayout,fragment);
        transaction.commit();
    }
}
