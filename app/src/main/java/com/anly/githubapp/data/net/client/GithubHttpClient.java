package com.anly.githubapp.data.net.client;

import android.app.Application;

import com.anly.githubapp.data.net.client.core.BaseOkHttpClient;
import com.anly.githubapp.data.pref.AccountPref;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mingjun on 16/7/20.
 */
public class GithubHttpClient extends BaseOkHttpClient {

    @Inject
    Application application;

    @Inject
    public GithubHttpClient() {
    }

    public String getAcceptHeader() {
        return "application/vnd.github.v3.json";
    }

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", getAcceptHeader())
                        .header("User-Agent", "GithubApp");

                if (AccountPref.isLogon(application)) {
                    requestBuilder
                        .header("Authorization", "token " + AccountPref.getLogonToken(application));
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return builder;
    }
}
