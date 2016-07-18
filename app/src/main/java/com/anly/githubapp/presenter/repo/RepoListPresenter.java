package com.anly.githubapp.presenter.repo;

import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.mvp.BaseMvpPresenter;
import com.anly.mvp.lce.LceView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/18.
 */
public class RepoListPresenter extends BaseMvpPresenter<LceView<ArrayList<Repo>>> {

    private final RepoApi mRepoApi;

    @Inject
    public RepoListPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void loadRepoList(String key, String language) {
        getMvpView().showLoading();
        mRepoApi.searchMostStarredRepo(key, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                });
    }
}
