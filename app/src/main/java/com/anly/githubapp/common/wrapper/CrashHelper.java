package com.anly.githubapp.common.wrapper;

import android.content.Context;

import com.anly.githubapp.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by mingjun on 16/8/16.
 */
public class CrashHelper {

    public static void init(Context context) {
        CrashReport.initCrashReport(context.getApplicationContext(), "daf1967bd3", BuildConfig.DEBUG);
    }

    public static void testCrash() {
        CrashReport.testJavaCrash();
    }

    public static void testAnr() {
        CrashReport.testANRCrash();
    }
}
