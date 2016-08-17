package com.anly.githubapp.ui.module.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.anly.githubapp.R;
import com.anly.githubapp.ui.base.BaseActivity;

/**
 * Created by mingjun on 16/8/17.
 */
public class ProfileActivity extends BaseActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
