package com.anly.githubapp.data.net.client;

import com.anly.githubapp.data.net.client.core.BaseOkHttpClient;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by mingjun on 16/7/20.
 */
public class NormalHttpClient extends BaseOkHttpClient {

    @Inject
    public NormalHttpClient() {
    }

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        return builder;
    }
}
