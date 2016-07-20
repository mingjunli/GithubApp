package com.anly.githubapp.data.net.client;

import com.anly.githubapp.data.net.client.core.ApiEndpoint;
import com.anly.githubapp.data.net.client.core.BaseRetrofit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Created by mingjun on 16/7/20.
 */
public class GithubRepoRetrofit extends BaseRetrofit {

    private static final String END_POINT = "https://api.github.com/";

    NormalHttpClient mHttpClient;

    @Inject
    public GithubRepoRetrofit(NormalHttpClient mHttpClient) {
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
