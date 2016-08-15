package com.anly.githubapp.presenter.main;

import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.githubapp.ui.module.repo.view.SearchView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/19.
 */
public class SearchPresenter extends RxMvpPresenter<SearchView<ArrayList<Repo>>> {

    private final RepoApi mRepoApi;

    @Inject
    public SearchPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void searchRepo(String key, String language) {
        mCompositeSubscription.add(mRepoApi.searchRepo(key, language)
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
                        getMvpView().error(e);
                    }

                    @Override
                    public void onSuccess(ArrayList<Repo> repos) {
                        getMvpView().showSearchResult(repos);
                    }
                }));
    }
}
