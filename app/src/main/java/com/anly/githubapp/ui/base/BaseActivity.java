package com.anly.githubapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.di.component.ActivityComponent;
import com.anly.githubapp.di.component.DaggerActivityComponent;
import com.anly.githubapp.di.module.ActivityModule;

/**
 * Created by mingjun on 16/7/16.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
