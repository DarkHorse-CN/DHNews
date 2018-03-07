package com.example.darkhorse.dhnews.callback;

import com.example.darkhorse.dhnews.bean.NewsBean;

import java.util.List;

/**
 * Created by DarkHorse on 2017/11/22.
 */

public interface NewsUpdateCallback {
    void onFailure();

    void onSuccess(List<NewsBean> list);
}
