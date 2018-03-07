package com.example.darkhorse.dhnews.ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.base.BaseActivity;

/**
 * Created by DarkHorse on 2018/2/22.
 */

public class VideoActivity extends BaseActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        findViewById();
        setListener();
        init();
    }

    @Override
    protected void findViewById() {
        mVideoView = findViewById(R.id.mVideoView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        initVideo();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String videoUrl = bundle.getString("videoUrl");
        mVideoView.setVideoURI(Uri.parse(videoUrl));
    }

    private void initVideo() {
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    public void onBackPressed() {
        if (mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
        }
        finish();
    }
}
