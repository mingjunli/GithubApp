package com.anly.githubapp.common.wrapper;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.Environment;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.anly.githubapp.BuildConfig;

/**
 * Created by mingjun on 16/8/17.
 */
public class PushPlatform {

    public static void init(final Context context) {
        if (BuildConfig.DEBUG) {
            AlibabaSDK.setSecGuardImagePostfix("debug");
        }

        AlibabaSDK.asyncInit(context.getApplicationContext(), new InitResultCallback() {
            @Override
            public void onSuccess() {
                AppLog.d("PushSDK, init onSuccess.");
                initCloudChannel(context);
            }

            @Override
            public void onFailure(int i, String s) {
                AppLog.w("PushSDK, init onFailure:" + s);
            }
        });
    }

    public static void initCloudChannel(Context context) {
        CloudPushService pushService = AlibabaSDK.getService(CloudPushService.class);

        AppLog.d("device id:" + pushService.getDeviceId());

        pushService.register(context.getApplicationContext(), new CommonCallback() {
            @Override
            public void onSuccess() {
                AppLog.d("PushSDK, initCloudChannel onSuccess.");

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                AppLog.w("PushSDK, initCloudChannel onFailure:" + errorMessage);
            }
        });
    }
}
