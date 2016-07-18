package com.anly.githubapp.di.module;

import com.anly.githubapp.data.RepoDataSource;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.api.impl.RetrofitBuilder;
import com.anly.githubapp.data.api.impl.service.RepoService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mingjun on 16/7/18.
 */
@Module
public class RepoModule {

    @Provides
    RepoService provideRepoService() {
        return RetrofitBuilder.build().create(RepoService.class);
    }

    @Provides
    RepoApi provideRepoApi(RepoDataSource dataSource) {
        return dataSource;
    }
}
