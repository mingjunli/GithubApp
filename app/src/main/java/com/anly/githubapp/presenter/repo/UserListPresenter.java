package com.anly.githubapp.presenter.repo;

import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.User;
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
public class UserListPresenter extends RxMvpPresenter<LceView<ArrayList<User>>> {

    private final RepoApi mRepoApi;

    @Inject
    public UserListPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void loadUsers(String username, boolean isSelf, @RepoApi.UserType int type) {
        Observable<ArrayList<User>> observable = null;

        switch (type) {
            case RepoApi.FOLLOWER:
                if (isSelf) {
                    observable = mRepoApi.getMyFollowers();
                }
                else {
                    observable = mRepoApi.getUserFollowers(username);
                }
                break;

            case RepoApi.FOLLOWING:
                if (isSelf) {
                    observable = mRepoApi.getMyFollowing();
                }
                else {
                    observable = mRepoApi.getUserFollowing(username);
                }
                break;

            default:
                break;
        }

        if (observable == null) return;

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
                .subscribe(new ResponseObserver<ArrayList<User>>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(ArrayList<User> repos) {
                        getMvpView().showContent(repos);
                    }
                }));
    }
}
