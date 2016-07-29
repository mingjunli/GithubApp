package com.anly.githubapp.di.component;

import com.anly.githubapp.di.PerActivity;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.di.module.TrendingRepoModule;
import com.anly.githubapp.ui.module.main.MostStarFragment;
import com.anly.githubapp.ui.module.main.SearchFragment;
import com.anly.githubapp.ui.module.main.TrendingFragment;
import com.anly.githubapp.ui.module.repo.RepoDetailActivity;

import dagger.Component;

/**
 * Created by mingjun on 16/7/7.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, RepoModule.class})
public interface RepoComponent extends ActivityComponent {

    void inject(RepoDetailActivity detailActivity);
}
