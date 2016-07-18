package com.anly.githubapp.di.module;

import android.app.Application;
import android.content.Context;


import com.anly.githubapp.data.RepoDataSource;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.api.impl.HttpClientBuilder;
import com.anly.githubapp.data.api.impl.RetrofitBuilder;
import com.anly.githubapp.data.api.impl.service.RepoService;
import com.anly.githubapp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {
        return HttpClientBuilder.build();
    }
}
