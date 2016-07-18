package com.anly.githubapp.data.api.impl.service;

import com.anly.githubapp.data.api.impl.response.SearchResultResp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mingjun on 16/7/18.
 */
public interface RepoService {

    @GET("search/repositories")
    Observable<SearchResultResp> searchRepo(@Query("q") String key, @Query("sort") String sort,
                                            @Query("order") String order, @Query("page") int page,
                                            @Query("per_page") int pageSize);
}
