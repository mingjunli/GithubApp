package com.anly.githubapp;

import android.app.Application;
import android.content.Context;

import com.anly.githubapp.common.config.MainMenuConfig;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.di.component.ApplicationComponent;
import com.anly.githubapp.di.component.DaggerApplicationComponent;
import com.anly.githubapp.di.module.ApplicationModule;

/**
 * Created by mingjun on 16/7/15.
 */
public class GithubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init logger.
        AppLog.init();
    }

    public static GithubApplication get(Context context) {
        return (GithubApplication) context.getApplicationContext();
    }

    ApplicationComponent mApplicationComponent;
    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

}
