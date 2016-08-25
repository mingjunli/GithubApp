package com.anly.githubapp.ui.module.main;

import android.os.Bundle;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.pref.AppPref;
import com.anly.githubapp.ui.base.BaseActivity;

/**
 * Created by mingjun on 16/8/23.
 */
public class LogoSplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppLog.d("trace===LogoSplashActivity onCreate");
        if (AppPref.isFirstRunning(this)) {
            IntroduceActivity.launch(this);
        }
        else {
            MainActivity.launch(this);
        }
        finish();
    }
}
