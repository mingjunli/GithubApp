package com.anly.githubapp.data.net.client;

import com.anly.githubapp.data.net.client.core.ApiEndpoint;
import com.anly.githubapp.data.net.client.core.BaseRetrofit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Created by mingjun on 16/7/20.
 */
public class GithubTrendingRetrofit extends BaseRetrofit {

    private static final String END_POINT = "http://githubtrending.herokuapp.com/";

    NormalHttpClient mHttpClient;

    @Inject
    public GithubTrendingRetrofit(NormalHttpClient mHttpClient) {
        this.mHttpClient = mHttpClient;
    }

    @Override
    public ApiEndpoint getApiEndpoint() {
        return new ApiEndpoint() {
            @Override
            public String getEndpoint() {
                return END_POINT;
            }
        };
    }

    @Override
    public OkHttpClient getHttpClient() {
        return mHttpClient.get();
    }
}
