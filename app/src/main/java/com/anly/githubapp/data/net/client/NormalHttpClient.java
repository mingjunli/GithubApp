package com.anly.githubapp.data.net.client;

import android.app.Application;
import android.util.Base64;

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
public class NormalHttpClient extends BaseOkHttpClient {

    @Inject
    public NormalHttpClient() {
    }

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        return builder;
    }
}
