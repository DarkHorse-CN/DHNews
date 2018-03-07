package com.example.darkhorse.dhnews.mvp.model;


import android.content.Context;

import com.example.darkhorse.dhnews.bean.NewsBean;
import com.example.darkhorse.dhnews.utils.DBManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DarkHorse on 2017/11/16.
 */

public class NewsModel implements INewsModel {

    private final int NEWS_LIST_COUNT = 10;

    @Override
    public void update(final Context context, List<NewsBean> newList, List<NewsBean> oldList) {
        List<NewsBean> tempList = new ArrayList<>();
        for (NewsBean bean : newList) {
            if (!oldList.contains(bean)) {
                tempList.add(bean);
            }
        }

        if (!tempList.isEmpty()) {
            DBManager.getInstance(context).insertNewsList(tempList);
            int size1 = tempList.size();
            int size2 = oldList.size();

            int sum = size1 + size2;
            int MAX_NEWS_COUNT = 100;
            if (size1 > 0 && sum > MAX_NEWS_COUNT) {
                size1 = sum - MAX_NEWS_COUNT;
                while (size1 > 0) {
                    DBManager.getInstance(context).deleteNews(oldList.get(oldList.size() - 1));
                    oldList.remove(oldList.size() - 1);
                    size1--;
                }
            }
        }
    }

    @Override
    public List<NewsBean> refresh(Context context, String type) {
        List<NewsBean> list = DBManager.getInstance(context).queryNewsListByType(type);
        if (!list.isEmpty()) {
            if (list.size() > NEWS_LIST_COUNT) {
                return list.subList(0, NEWS_LIST_COUNT);
            } else {
                return list.subList(0, list.size());
            }
        } else {
            return null;
        }
    }

    @Override
    public List<NewsBean> loadMore(Context context, String type, int fromIndex) {
        List<NewsBean> list = DBManager.getInstance(context).queryNewsListByType(type);
        if (!list.isEmpty()) {
            if (list.size() > fromIndex + NEWS_LIST_COUNT - 1) {
                return list.subList(fromIndex, fromIndex + NEWS_LIST_COUNT - 1);
            } else {
                return list.subList(fromIndex, list.size() - 1);
            }
        } else {
            return null;
        }
    }
}
