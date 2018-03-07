package com.example.darkhorse.dhnews.mvp.model;


import android.content.Context;

import com.example.darkhorse.dhnews.bean.NewsBean;
import com.example.darkhorse.dhnews.mvp.IModel;

import java.util.List;

/**
 * Created by DarkHorse on 2017/11/16.
 */

public interface INewsModel extends IModel {
    void update(Context context, List<NewsBean> newList, List<NewsBean> oldList);

    List<NewsBean> refresh(Context context, String type);

    List<NewsBean> loadMore(Context context, String type,int fromIndex);
}
