package com.anly.githubapp.di.module;

import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.net.TrendingRepoDataSource;
import com.anly.githubapp.data.net.client.GithubTrendingRetrofit;
import com.anly.githubapp.data.net.service.TrendingService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by mingjun on 16/7/18.
 */
@Module
public class TrendingRepoModule {

    @Provides
    TrendingApi provideTrendingApi(TrendingRepoDataSource dataSource) {
        return dataSource;
    }
}
