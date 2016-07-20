package com.anly.githubapp.data.net.service;

import com.anly.githubapp.data.net.response.TrendingResultResp;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by mingjun on 16/7/20.
 */
public interface TrendingService {

    @GET("trending?languages[]=java&languages[]=objective-c&languages[]=bash&languages[]=python&languages[]=html")
    Observable<TrendingResultResp> getTrendingRepos();

}
