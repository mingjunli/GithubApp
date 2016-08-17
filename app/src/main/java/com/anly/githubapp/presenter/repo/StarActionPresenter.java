package com.anly.githubapp.presenter.repo;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.githubapp.ui.module.repo.view.RepoView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/8/15.
 */
public class StarActionPresenter extends RxMvpPresenter<RepoView> {

    private final RepoApi mRepoApi;

    @Inject
    public StarActionPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void starRepo(String owner, String repo) {
        mCompositeSubscription.add(mRepoApi.starRepo(owner, repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
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
                .subscribe(new ResponseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            getMvpView().starSuccess();
                        } else {
                            getMvpView().starFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                        getMvpView().starFailed();
                    }
                }));
    }

    public void unstarRepo(String owner, String repo) {
        mCompositeSubscription.add(mRepoApi.unstarRepo(owner, repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
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
                .subscribe(new ResponseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            getMvpView().unstarSuccess();
                        } else {
                            getMvpView().unstarFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                        getMvpView().unstarFailed();
                    }
                }));
    }
}
