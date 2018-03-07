package com.example.darkhorse.dhnews.callback;


import com.example.darkhorse.dhnews.bean.NewsBean;

import java.util.List;


/**
 * Created by DarkHorse on 2017/11/20.
 */

public interface NewsLoadMoreCallback {
    void onFailure();

    void onSuccess(List<NewsBean> list);
}
