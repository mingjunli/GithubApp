package com.anly.githubapp.di.component;

import android.app.Activity;

import com.anly.githubapp.di.PerActivity;
import com.anly.githubapp.di.module.ActivityModule;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
