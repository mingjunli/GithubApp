package com.anly.githubapp.data.api;

import com.anly.githubapp.data.model.User;

import rx.Observable;

/**
 * Created by mingjun on 16/7/27.
 */
public interface AccountApi {

    Observable<User> login(String username, String password);

    boolean isLogon();

    User getLogonUser();

    String getLogonToken();
}
