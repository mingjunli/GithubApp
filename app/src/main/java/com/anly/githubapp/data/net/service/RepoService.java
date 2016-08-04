package com.anly.githubapp.data.net.service;

import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.data.net.response.SearchResultResp;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("repos/{owner}/{name}")
    Observable<Repo> get(@Path("owner") String owner, @Path("name") String repo);

    @GET("repos/{owner}/{name}/contributors")
    Observable<ArrayList<User>> contributors(@Path("owner") String owner, @Path("name") String repo);

    @GET("repos/{owner}/{name}/readme")
    Observable<Content> readme(@Path("owner") String owner, @Path("name") String repo);

    @GET("repos/{owner}/{name}/forks")
    Observable<ArrayList<Repo>> listForks(@Path("owner") String owner, @Path("name") String repo,
                         @Query("sort") String sort);

    @GET("user/repos")
    Observable<ArrayList<Repo>> getMyRepos();
}
