package com.example.darkhorse.dhnews.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseActivity;
import com.example.darkhorse.dhnews.base.BaseFragment;
import com.example.darkhorse.dhnews.ui.fragment.NewsProxyFragment;
import com.example.darkhorse.dhnews.ui.fragment.VideoProxyFragment;

public class TabActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_tab_01, ll_tab_02, ll_tab_03, ll_tab_04;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private NewsProxyFragment mNewsProxyFragment;
    private VideoProxyFragment mVideoProxyFragment;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        findViewById();
        setListener();
        init();
        setImmersion();
    }

    @Override
    protected void findViewById() {
        ll_tab_01 = (LinearLayout) findViewById(R.id.ll_tab_01);
        ll_tab_02 = (LinearLayout) findViewById(R.id.ll_tab_02);
        ll_tab_03 = (LinearLayout) findViewById(R.id.ll_tab_03);
        ll_tab_04 = (LinearLayout) findViewById(R.id.ll_tab_04);
    }

    @Override
    protected void setListener() {
        ll_tab_01.setOnClickListener(this);
        ll_tab_02.setOnClickListener(this);
        ll_tab_03.setOnClickListener(this);
        ll_tab_04.setOnClickListener(this);
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
        ll_tab_01.callOnClick();
    }

    private void resetTab() {
        ll_tab_01.setSelected(false);
        ll_tab_02.setSelected(false);
        ll_tab_03.setSelected(false);
        ll_tab_04.setSelected(false);

    }

    private void hideFragments() {
        if (currentFragment != null) {
            mFragmentTransaction.hide(currentFragment);
        }
    }

    private void selectTab(int i) {
        resetTab();

        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments();

        switch (i) {
            case 1:
                ll_tab_01.setSelected(true);
                if (mNewsProxyFragment == null) {
                    mNewsProxyFragment = new NewsProxyFragment();
                    mFragmentTransaction.add(R.id.mFrameLayout, mNewsProxyFragment);
                } else {
                    mFragmentTransaction.show(mNewsProxyFragment);
                }
                currentFragment = mNewsProxyFragment;
                break;
            case 2:
                ll_tab_02.setSelected(true);
                if (mVideoProxyFragment == null) {
                    mVideoProxyFragment = new VideoProxyFragment();
                    mFragmentTransaction.add(R.id.mFrameLayout, mVideoProxyFragment);
                } else {
                    mFragmentTransaction.show(mVideoProxyFragment);
                }
                currentFragment = mVideoProxyFragment;
                break;
            case 3:
                ll_tab_03.setSelected(true);
                break;
            case 4:
                ll_tab_04.setSelected(true);
                break;
            default:
        }

        mFragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_01:
                selectTab(1);
                ll_tab_01.setSelected(true);
                break;
            case R.id.ll_tab_02:
                selectTab(2);
                break;
            case R.id.ll_tab_03:
                selectTab(3);
                break;
            case R.id.ll_tab_04:
                selectTab(4);
                break;
            default:
        }
    }

//    @Override
//    public void onBackPressed() {
//        if(currentFragment.equals(mVideoProxyFragment)){
//            mVideoProxyFragment.onBackPressed();
//            Log.i("tag","true");
//        }else{
//            super.onBackPressed();
//        }
//    }
}
