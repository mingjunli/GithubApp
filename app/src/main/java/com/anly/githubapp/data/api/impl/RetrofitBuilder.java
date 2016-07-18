package com.anly.githubapp.data.api.impl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mingjun on 16/6/28.
 */

public class RetrofitBuilder {

    private static final String END_POINT = "https://api.github.com/";

    private static Retrofit mRetrofit;

    public static Retrofit build() {
        if (mRetrofit != null) {
            return mRetrofit;
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .client(HttpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return mRetrofit;
    }
}
