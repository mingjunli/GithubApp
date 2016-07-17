package com.anly.githubapp;

import android.app.Application;

import com.anly.githubapp.common.util.AppLog;

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
}
