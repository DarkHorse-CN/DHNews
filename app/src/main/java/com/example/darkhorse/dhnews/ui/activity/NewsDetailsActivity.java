package com.example.darkhorse.dhnews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseActivity;

/**
 * Created by DarkHorse on 2017/12/7.
 */

public class NewsDetailsActivity extends BaseActivity {
    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        findViewById();
        setListener();
        init();
    }

    @Override
    protected void findViewById() {
        mWebView = (WebView) findViewById(R.id.mWebview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        mWebSettings = mWebView.getSettings();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String url = bundle.getString("url");
        mWebView.loadUrl(url);
    }
}
