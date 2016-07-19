package com.anly.githubapp.di.component;

import com.anly.githubapp.di.PerActivity;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.ui.module.main.MostStarFragment;
import com.anly.githubapp.ui.module.repo.RepoListActivity;

import dagger.Component;

/**
 * Created by mingjun on 16/7/7.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, RepoModule.class})
public interface MainComponent extends ActivityComponent {

    void inject(MostStarFragment mostStarFragment);
}
