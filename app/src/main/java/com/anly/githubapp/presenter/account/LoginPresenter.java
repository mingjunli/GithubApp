package com.anly.githubapp.presenter.account;

import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.api.AccountApi;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.mvp.lce.LceView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/27.
 */
public class LoginPresenter extends RxMvpPresenter<LceView<User>> {

    private final AccountApi mAccountApi;

    @Inject
    public LoginPresenter(AccountApi api) {
        this.mAccountApi = api;
    }

    public void login(String username, String password) {
        mCompositeSubscription.add(mAccountApi.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        AppLog.d("user:" + user.getLogin());
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                    }
                }));
    }
}
