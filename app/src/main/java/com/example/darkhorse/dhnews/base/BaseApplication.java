package com.example.darkhorse.dhnews.base;

import android.app.Application;

import com.darkhorse.httpmanager.HttpManager;
import com.darkhorse.preferencesmanager.PreferencesUtils;
import com.example.darkhorse.dhnews.R;
import com.example.darkhorse.dhnews.utils.constant.API;
import com.example.darkhorse.dhnews.utils.constant.PreferenceKey;


/**
 * Created by DarkHorse on 2017/11/22.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if ((Boolean) PreferencesUtils.get(this, PreferenceKey.IS_FIRST, true)) {
            String[] types = getResources().getStringArray(R.array.array_news_type);
            String[] titles = getResources().getStringArray(R.array.array_tab_title);
            for (int i = 0; i < types.length; i++) {
                PreferencesUtils.put(this, types[i], titles[i]);
            }
            PreferencesUtils.put(this, PreferenceKey.IS_FIRST, false);
        }
        initHttManager();
    }

    private void initHttManager() {
        HttpManager.getInstance()
                .addBaseUrl(API.NEWS_URL)
                .init();
    }
}
