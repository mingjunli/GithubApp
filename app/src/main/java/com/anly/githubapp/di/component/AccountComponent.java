package com.anly.githubapp.di.component;

import com.anly.githubapp.di.PerActivity;
import com.anly.githubapp.di.module.AccountModule;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.ui.module.account.LoginActivity;

import dagger.Component;

/**
 * Created by mingjun on 16/7/7.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, AccountModule.class})
public interface AccountComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);
}
