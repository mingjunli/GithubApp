package com.anly.githubapp.data.net.client;

import android.app.Application;

import com.anly.githubapp.common.util.NetworkUtil;
import com.anly.githubapp.data.net.client.core.BaseOkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mingjun on 16/7/20.
 */
public class CacheHttpClient extends BaseOkHttpClient {

    private static final long CACHE_SIZE = 1024 * 1024 * 50;

    @Inject
    Application mContext;

    @Inject
    public CacheHttpClient() {
    }

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        File cacheFile = new File(mContext.getCacheDir(), "github_repo");
        Cache cache = new Cache(cacheFile, CACHE_SIZE);
        builder.cache(cache);

        builder.addNetworkInterceptor(mCacheControlInterceptor);
        return builder;
    }

    private final Interceptor mCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkAvailable(mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);

            if (NetworkUtil.isNetworkAvailable(mContext)) {

                String cacheControl = request.cacheControl().toString();

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                        .build();
            }
        }
    };
}
