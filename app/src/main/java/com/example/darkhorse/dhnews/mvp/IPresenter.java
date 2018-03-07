package com.example.darkhorse.dhnews.mvp;

/**
 * Created by DarkHorse on 2017/11/20.
 */

public interface IPresenter <T extends IView>{
    //绑定IView
    void attachView(T view);

    //解绑IView
    void detachView();
}
