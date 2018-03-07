package com.example.darkhorse.dhnews.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.darkhorse.dhnews.bean.NewsBean;
import com.example.darkhorse.dhnews.greendao.DaoMaster;
import com.example.darkhorse.dhnews.greendao.DaoSession;
import com.example.darkhorse.dhnews.greendao.NewsBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by DarkHorse on 2017/11/22.
 */

public class DBManager {

    private static Context mContext;
    private final static String DB_NAME = "DHNewsDB";
    private DaoMaster.DevOpenHelper mOpenHelper;

    public DBManager() {
        mOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME);
    }

    public static DBManager getInstance(Context context) {
        mContext = context;
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static DBManager INSTANCE = new DBManager();
    }

    public SQLiteDatabase getReadableDatabase() {
        return mOpenHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return mOpenHelper.getWritableDatabase();
    }

    public DaoSession getSession(boolean writable) {
        if (writable) {
            return new DaoMaster(getWritableDatabase()).newSession();
        }
        return new DaoMaster(getReadableDatabase()).newSession();
    }

    public NewsBeanDao getNewsBeanDao(boolean writable) {
        return getSession(writable).getNewsBeanDao();
    }

    /**
     * 插入一条新闻
     *
     * @param news
     */
    public void insertNews(NewsBean news) {
        getNewsBeanDao(true).updateInTx(news);
    }

    /**
     * 插入一组新闻
     *
     * @param newsList
     */
    public void insertNewsList(List<NewsBean> newsList) {
        getNewsBeanDao(true).insertInTx(newsList);
    }

    /**
     * 删除一条新闻
     *
     * @param news
     */
    public void deleteNews(NewsBean news) {
        getNewsBeanDao(true).delete(news);
    }


    public void deleteALlNews() {
        getNewsBeanDao(true).deleteAll();
    }

    /**
     * 更新一条新闻
     *
     * @param news
     */
    public void updateNews(NewsBean news) {
        getNewsBeanDao(true).update(news);
    }

    /**
     * 查询所有新闻
     *
     * @return
     */
    public List<NewsBean> queryNewsListAll() {
        QueryBuilder<NewsBean> queryBuilder = getNewsBeanDao(false).queryBuilder();
        queryBuilder.orderDesc(NewsBeanDao.Properties.Date);
        List<NewsBean> list = queryBuilder.list();
        return list;
    }

    /**
     * 根据新闻类型查询
     *
     * @param type
     * @return
     */
    public List<NewsBean> queryNewsListByType(String type) {
        QueryBuilder<NewsBean> queryBuilder = getNewsBeanDao(false).queryBuilder();
        queryBuilder.where(NewsBeanDao.Properties.Category.eq(type)).orderDesc(NewsBeanDao.Properties.Date);
        List<NewsBean> list = queryBuilder.list();
        return list;
    }

}
