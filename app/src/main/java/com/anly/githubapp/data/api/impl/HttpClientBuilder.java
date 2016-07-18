package com.anly.githubapp.data.api.impl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by mingjun on 16/7/6.
 */
public class HttpClientBuilder {

    private static final long TIMEOUT_CONNECT = 30 * 1000;

    private static OkHttpClient mClient;

    public static OkHttpClient build() {
        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }

        return mClient;
    }
}
