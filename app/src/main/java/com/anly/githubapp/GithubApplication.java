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
        AppLog.d("trace===GithubApplication before onCreate");
        super.onCreate();
        Debug.startMethodTracing("GithubApp-1");
        AppLog.d("trace===GithubApplication onCreate");

        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                // init logger.
                AppLog.init();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {

                // init crash helper
                CrashHelper.init(GithubApplication.this);
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {

                // init Push
                PushPlatform.init(GithubApplication.this);
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {

                // init Feedback
                FeedbackPlatform.init(GithubApplication.this);
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {

                // init Share
                SharePlatform.init(GithubApplication.this);
            }
        }).subscribeOn(Schedulers.newThread());

        // init Drawer image loader
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                ImageLoader.loadWithCircle(GithubApplication.this, uri, imageView);
            }
        });
        AppLog.d("trace===GithubApplication onCreate end");
        Debug.stopMethodTracing();
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
