package com.example.darkhorse.dhnews.mvp.presenter;


import com.example.darkhorse.dhnews.mvp.IPresenter;
import com.example.darkhorse.dhnews.mvp.view.INewsView;

/**
 * Created by DarkHorse on 2017/11/20.
 */

public interface INewsPresenter extends IPresenter<INewsView> {
    void update();

    void refresh();

    void loadMore(int fromIndex);
}
