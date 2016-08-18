package com.anly.githubapp.data.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.anly.githubapp.data.model.User;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.google.gson.Gson;

/**
 * Created by mingjun on 16/7/27.
 */
public class AccountPref {

    private static final String KEY_LOGIN_TOKEN = "login_token";
    private static final String KEY_LOGON_USER = "logon_user";

    private static SharedPreferences getPreference(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences("com.anly.githubapp.account_preference.xml", Context.MODE_PRIVATE);
    }

    public static void saveLoginToken(Context context, String loginToken) {
        getPreference(context).edit().putString(KEY_LOGIN_TOKEN, loginToken).apply();
    }

    public static String getLogonToken(Context context) {
        return getPreference(context).getString(KEY_LOGIN_TOKEN, "");
    }

    public static void saveLogonUser(Context context, User user) {
        String userJson = new Gson().toJson(user);
        getPreference(context).edit().putString(KEY_LOGON_USER, userJson).apply();
    }

    public static void removeLogonUser(Context context) {
        getPreference(context).edit().remove(KEY_LOGON_USER).apply();
    }

    public static User getLogonUser(Context context) {
        User user = null;
        String userJson = getPreference(context).getString(KEY_LOGON_USER, "");
        if (!TextUtils.isEmpty(userJson)) {
            user = new Gson().fromJson(userJson, User.class);
        }
        return user;
    }

    public static boolean isLogon(Context context) {
        return !TextUtils.isEmpty(getLogonToken(context)) && getLogonUser(context) != null;
    }

    public static boolean checkLogon(Context context) {
        if (!isLogon(context)) {
            LoginActivity.launch(context);
            return false;
        }

        return true;
    }
}
