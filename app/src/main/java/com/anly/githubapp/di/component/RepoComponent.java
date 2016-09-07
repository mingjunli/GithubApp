package com.anly.githubapp.di.component;

import com.anly.githubapp.di.PerActivity;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.ui.module.account.UserActivity;
import com.anly.githubapp.ui.module.repo.CodeActivity;
import com.anly.githubapp.ui.module.repo.RepoDetailActivity;
import com.anly.githubapp.ui.module.repo.RepoListActivity;
import com.anly.githubapp.ui.module.repo.RepoTreeActivity;
import com.anly.githubapp.ui.module.repo.SearchActivity;
import com.anly.githubapp.ui.module.repo.UserListActivity;

import dagger.Component;

/**
 * Created by mingjun on 16/7/7.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, RepoModule.class})
public interface RepoComponent extends ActivityComponent {

    void inject(SearchActivity searchActivity);

    void inject(RepoListActivity listActivity);
    void inject(RepoDetailActivity detailActivity);
    void inject(RepoTreeActivity treeActivity);
    void inject(CodeActivity codeActivity);

    void inject(UserListActivity userListActivity);
    void inject(UserActivity userActivity);
}
