package com.anly.githubapp.di.module;

import android.app.Application;
import android.content.Context;


import com.anly.githubapp.data.net.client.GithubAuthRetrofit;
import com.anly.githubapp.data.net.client.GithubRepoRetrofit;
import com.anly.githubapp.data.net.client.GithubTrendingRetrofit;
import com.anly.githubapp.data.net.service.AccountService;
import com.anly.githubapp.data.net.service.RepoService;
import com.anly.githubapp.data.net.service.TrendingService;
import com.anly.githubapp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    RepoService provideRepoService(GithubRepoRetrofit retrofit) {
        return retrofit.get().create(RepoService.class);
    }

    @Provides
    @Singleton
    TrendingService provideTrendingService(GithubTrendingRetrofit retrofit) {
        return retrofit.get().create(TrendingService.class);
    }

}
