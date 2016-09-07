package com.anly.githubapp.presenter.repo;

import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.mvp.lce.LceView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/19.
 */
public class CodePresenter extends RxMvpPresenter<LceView<Content>> {

    private final RepoApi mRepoApi;

    @Inject
    public CodePresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void getContentDetail(String owner, String repo, String path) {
        mCompositeSubscription.add(mRepoApi.getContentDetail(owner, repo, path)
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
                .subscribe(new ResponseObserver<Content>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(Content content) {
                        getMvpView().showContent(content);
                    }
                }));
    }
}
