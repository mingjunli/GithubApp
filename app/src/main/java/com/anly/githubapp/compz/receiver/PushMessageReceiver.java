package com.anly.githubapp.compz.receiver;

import android.content.Context;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.anly.githubapp.common.wrapper.AppLog;

import java.util.Map;

/**
 * Created by mingjun on 16/8/17.
 */
public class PushMessageReceiver extends MessageReceiver {

    @Override
    protected void onNotification(Context context, String s, String s1, Map<String, String> map) {
        super.onNotification(context, s, s1, map);
        AppLog.d("onNotification:" + s);
    }

    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        AppLog.d("onMessage:" + cPushMessage.getTitle());
    }

    @Override
    protected void onNotificationOpened(Context context, String s, String s1, String s2) {
        super.onNotificationOpened(context, s, s1, s2);
    }

    @Override
    protected void onNotificationRemoved(Context context, String s) {
        super.onNotificationRemoved(context, s);
    }
}
