package com.anly.githubapp.ui.base;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.di.component.ActivityComponent;
import com.anly.githubapp.di.component.DaggerActivityComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

/**
 * Created by mingjun on 16/7/16.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // define the IconicsLayoutInflater
        // this is compatible with calligraphy and other libs which wrap the baseContext
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(GithubApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
