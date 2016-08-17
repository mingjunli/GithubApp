package com.anly.githubapp.ui.module.setting;

import android.content.Context;
import android.content.Intent;

import com.anly.githubapp.ui.base.BaseActivity;

/**
 * Created by mingjun on 16/8/15.
 */
public class SettingsActivity extends BaseActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
