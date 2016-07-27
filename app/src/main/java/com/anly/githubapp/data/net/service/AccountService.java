package com.anly.githubapp.data.net.service;

import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.request.CreateAuthorization;
import com.anly.githubapp.data.net.response.AuthorizationResp;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mingjun on 16/7/27.
 */
public interface AccountService {

    @POST("/authorizations")
    Observable<AuthorizationResp> createAuthorization(
            @Body CreateAuthorization createAuthorization);

    @GET("/user")
    Observable<User> getUserInfo(@Query("access_token") String accessToken);
}
