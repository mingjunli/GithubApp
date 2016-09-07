package com.anly.githubapp.presenter.account;

import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.mvp.lce.LceView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/9/5.
 */
public class UserPresenter extends RxMvpPresenter<LceView<User>> {

    private final RepoApi mRepoApi;

    @Inject
    public UserPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void getSingleUser(String name) {
        mCompositeSubscription.add(mRepoApi.getSingleUser(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().dismissLoading();
                    }
                })
                .subscribe(new ResponseObserver<User>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(User user) {
                        getMvpView().showContent(user);
                    }
                }));
    }
}
