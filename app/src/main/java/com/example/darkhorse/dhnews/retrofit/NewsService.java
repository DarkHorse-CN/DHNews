package com.example.darkhorse.dhnews.retrofit;

import com.example.darkhorse.dhnews.bean.NewsJson;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DarkHorse on 2017/11/27.
 */

public interface NewsService {
    @GET("toutiao/index")
    Observable<NewsJson> getNewsJson(@Query("type") String type, @Query("key") String key);
}
