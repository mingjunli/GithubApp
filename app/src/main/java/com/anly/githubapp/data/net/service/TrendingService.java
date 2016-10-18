package com.anly.githubapp.data.net.service;

import com.anly.githubapp.data.model.TrendingRepo;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mingjun on 16/7/20.
 */
public interface TrendingService {

    @Headers("Cache-Control: public, max-age=600")
    @GET("api/github/trending/{lang}")
    Observable<ArrayList<TrendingRepo>> getTrendingRepos(
            @Path("lang") String lang, @Query("since") String since);
}
