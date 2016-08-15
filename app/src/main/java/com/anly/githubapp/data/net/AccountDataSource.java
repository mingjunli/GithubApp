package com.anly.githubapp.data.net;

import android.app.Application;
import android.text.TextUtils;

import com.anly.githubapp.common.config.GithubConfig;
import com.anly.githubapp.data.api.AccountApi;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.client.GithubAuthRetrofit;
import com.anly.githubapp.data.net.request.CreateAuthorization;
import com.anly.githubapp.data.net.response.AuthorizationResp;
import com.anly.githubapp.data.net.service.AccountService;
import com.anly.githubapp.data.pref.AccountPref;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mingjun on 16/7/27.
 */
public class AccountDataSource implements AccountApi {

    @Inject
    GithubAuthRetrofit mRetrofit;

    @Inject
    Application mContext;

    @Inject
    public AccountDataSource() {
    }

    @Override
    public Observable<User> login(String username, String password) {
        mRetrofit.setAuthInfo(username, password);
        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        CreateAuthorization createAuthorization = new CreateAuthorization();
        createAuthorization.note = GithubConfig.NOTE;
        createAuthorization.client_id = GithubConfig.CLIENT_ID;
        createAuthorization.client_secret = GithubConfig.CLIENT_SECRET;
        createAuthorization.scopes = GithubConfig.SCOPES;

        return accountService.createAuthorization(createAuthorization)
                    .flatMap(new Func1<AuthorizationResp, Observable<User>>() {
                        @Override
                        public Observable<User> call(AuthorizationResp authorizationResp) {

                            String token = authorizationResp.getToken();

                            // save token
                            AccountPref.saveLoginToken(mContext, token);

                            return accountService.getUserInfo(authorizationResp.getToken());
                        }
                    });
    }
}
