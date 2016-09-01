package com.anly.githubapp.common.wrapper;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;

/**
 * Created by mingjun on 16/8/18.
 */
public class FeedbackPlatform {

    public static void init(Application application) {
        AppLog.d("FeedbackPlatform init");
        FeedbackAPI.initAnnoy(application, "23436013");
    }

    public static void openFeedback(Context context) {
        FeedbackAPI.openFeedbackActivity(context);
    }
}
