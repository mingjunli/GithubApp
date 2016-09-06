package com.anly.githubapp.presenter.repo;

import android.content.Context;

import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.mvp.lce.LceView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/19.
 */
public class RepoListPresenter extends RxMvpPresenter<LceView<ArrayList<Repo>>> {

    private final RepoApi mRepoApi;

    @Inject
    public RepoListPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void loadRepos(Context context, String username) {

        Observable<ArrayList<Repo>> observable;
        if (AccountPref.isSelf(context, username)) {
            observable = mRepoApi.getMyRepos();
        }
        else {
            observable = mRepoApi.getUserRepos(username);
        }

        mCompositeSubscription.add(observable
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
                .subscribe(new ResponseObserver<ArrayList<Repo>>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(ArrayList<Repo> repos) {
                        getMvpView().showContent(repos);
                    }
                }));
    }

    public void loadMyRepos() {
        mCompositeSubscription.add(mRepoApi.getMyRepos()
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
                .subscribe(new ResponseObserver<ArrayList<Repo>>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(ArrayList<Repo> repos) {
                        getMvpView().showContent(repos);
                    }
                }));
    }
}
