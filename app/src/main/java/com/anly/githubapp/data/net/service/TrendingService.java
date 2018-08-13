package com.anly.githubapp.data.net.service;

import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.data.net.response.TrendingResultResp;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mingjun on 16/7/20.
 */
public interface TrendingService {

    @Headers("Cache-Control: public, max-age=600")
    @GET("trending/{lang}")
    Observable<ArrayList<TrendingRepo>> getTrendingRepos(@Path("lang") String lang);

}
