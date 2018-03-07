package com.example.darkhorse.dhnews.bean;

import java.util.List;

/**
 * Created by DarkHorse on 2017/11/28.
 */

public class NewsResult {
    private int stat;
    private List<NewsBean> data;

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public List<NewsBean> getData() {
        return data;
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
    }
}
