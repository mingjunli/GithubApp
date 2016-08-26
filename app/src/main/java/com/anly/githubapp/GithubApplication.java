package com.anly.githubapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Debug;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.common.wrapper.CrashHelper;
import com.anly.githubapp.common.wrapper.FeedbackPlatform;
import com.anly.githubapp.common.wrapper.ImageLoader;
import com.anly.githubapp.common.wrapper.PushPlatform;
import com.anly.githubapp.common.wrapper.SharePlatform;
import com.anly.githubapp.compz.service.InitializeService;
import com.anly.githubapp.di.component.ApplicationComponent;
import com.anly.githubapp.di.component.DaggerApplicationComponent;
import com.anly.githubapp.di.module.ApplicationModule;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/15.
 */
public class GithubApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // init logger.
        AppLog.init();

        InitializeService.start(this);
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
