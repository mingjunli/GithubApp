package com.anly.githubapp.data.net.service;

import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.data.net.response.SearchResultResp;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mingjun on 16/7/18.
 */
public interface RepoService {

    @Headers("Cache-Control: public, max-age=600")
    @GET("search/repositories")
    Observable<SearchResultResp> searchRepo(@Query("q") String key, @Query("sort") String sort,
                                            @Query("order") String order, @Query("page") int page,
                                            @Query("per_page") int pageSize);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("repos/{owner}/{name}")
    Observable<Repo> get(@Path("owner") String owner, @Path("name") String repo);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("repos/{owner}/{name}/contributors")
    Observable<ArrayList<User>> contributors(@Path("owner") String owner, @Path("name") String repo);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("repos/{owner}/{name}/readme")
    Observable<Content> readme(@Path("owner") String owner, @Path("name") String repo);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("repos/{owner}/{name}/forks")
    Observable<ArrayList<Repo>> listForks(@Path("owner") String owner, @Path("name") String repo,
                         @Query("sort") String sort);

    @Headers("Cache-Control: public, max-age=600")
    @GET("user/repos")
    Observable<ArrayList<Repo>> getMyRepos();

    @Headers("Cache-Control: public, max-age=600")
    @GET("user/starred")
    Observable<ArrayList<Repo>> getMyStarredRepos();

    @Headers("Content-Length: 0")
    @PUT("/user/starred/{owner}/{repo}")
    Observable<Response<ResponseBody>> starRepo(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/user/starred/{owner}/{repo}")
    Observable<Response<ResponseBody>> checkIfRepoIsStarred(@Path("owner") String owner, @Path("repo") String repo);

    @DELETE("/user/starred/{owner}/{repo}")
    Observable<Response<ResponseBody>> unstarRepo(@Path("owner") String owner, @Path("repo") String repo);
}
