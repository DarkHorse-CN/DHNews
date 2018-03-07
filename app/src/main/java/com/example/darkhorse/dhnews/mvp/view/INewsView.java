package com.example.darkhorse.dhnews.mvp.view;


import com.example.darkhorse.dhnews.bean.NewsBean;
import com.example.darkhorse.dhnews.mvp.IView;

import java.util.List;

/**
 * Created by DarkHorse on 2017/11/16.
 */

public interface INewsView extends IView {
    String getType();

    void refresh(List<NewsBean> list);

    void loadMore(List<NewsBean> list);
}
