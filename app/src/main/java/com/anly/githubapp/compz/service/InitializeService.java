package com.anly.githubapp.compz.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.common.wrapper.CrashHelper;
import com.anly.githubapp.common.wrapper.FeedbackPlatform;
import com.anly.githubapp.common.wrapper.ImageLoader;
import com.anly.githubapp.common.wrapper.PushPlatform;
import com.anly.githubapp.common.wrapper.SharePlatform;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

/**
 * Created by mingjun on 16/8/25.
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.anly.githubapp.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        AppLog.d("performInit begin:" + System.currentTimeMillis());

        // init Drawer image loader
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                ImageLoader.loadWithCircle(getApplicationContext(), uri, imageView);
            }
        });

        // init crash helper
        CrashHelper.init(this.getApplicationContext());

        // init Push
        PushPlatform.init(this.getApplicationContext());

        // init Feedback
        FeedbackPlatform.init(this.getApplication());

        // init Share
        SharePlatform.init(this.getApplicationContext());

        AppLog.d("performInit end:" + System.currentTimeMillis());
    }
}