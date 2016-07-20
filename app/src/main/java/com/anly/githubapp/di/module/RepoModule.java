package com.anly.githubapp.di.module;

import com.anly.githubapp.data.net.RepoDataSource;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.net.client.GithubRepoRetrofit;
import com.anly.githubapp.data.net.service.RepoService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by mingjun on 16/7/18.
 */
@Module
public class RepoModule {

    @Provides
    RepoApi provideRepoApi(RepoDataSource dataSource) {
        return dataSource;
    }
}
