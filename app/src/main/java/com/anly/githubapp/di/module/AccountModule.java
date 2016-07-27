package com.anly.githubapp.di.module;

import com.anly.githubapp.data.api.AccountApi;
import com.anly.githubapp.data.net.AccountDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mingjun on 16/7/27.
 */
@Module
public class AccountModule {

    @Provides
    AccountApi provideAccountApi(AccountDataSource dataSource) {
        return dataSource;
    }
}
