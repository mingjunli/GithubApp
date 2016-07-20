package com.anly.githubapp.di.component;

import android.app.Application;
import android.content.Context;

import com.anly.githubapp.data.net.service.RepoService;
import com.anly.githubapp.data.net.service.TrendingService;
import com.anly.githubapp.di.ApplicationContext;
import com.anly.githubapp.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    RepoService repoService();

    TrendingService trendingService();
}
