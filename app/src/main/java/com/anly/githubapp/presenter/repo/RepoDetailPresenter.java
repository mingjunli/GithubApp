package com.anly.githubapp.presenter.repo;

import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.githubapp.ui.module.repo.view.RepoDetailView;
import com.anly.mvp.lce.LceView;
import com.anly.mvp.lce.LoadView;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/29.
 */
public class RepoDetailPresenter extends RxMvpPresenter<RepoDetailView> {

    private final RepoApi mRepoApi;

    @Inject
    public RepoDetailPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void loadRepoDetails(String owner, String repo) {
        AppLog.d("loadRepoDetails, owner:" + owner + ", repo:" + repo);
        mCompositeSubscription.add(
                mRepoApi.getRepoDetail(owner, repo)
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
                        .subscribe(new ResponseObserver<RepoDetail>() {
                            @Override
                            public void onSuccess(RepoDetail detail) {
                                getMvpView().showRepoDetail(detail);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getMvpView().error(e);
                            }
                        })
        );
    }
}
