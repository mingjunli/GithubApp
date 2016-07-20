package com.anly.githubapp.presenter.main;

import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.mvp.BaseMvpPresenter;
import com.anly.mvp.lce.LceView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/20.
 */
public class TrendingRepoPresenter extends RxMvpPresenter<LceView<ArrayList<TrendingRepo>>> {

    private final TrendingApi mTrendingApi;

    @Inject
    public TrendingRepoPresenter(TrendingApi api) {
        this.mTrendingApi = api;
    }

    public void loadTrendingRepo(@TrendingApi.LanguageType int languageType) {
        mCompositeSubscription.add(mTrendingApi.getTrendingRepo(languageType)
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
                .subscribe(new ResponseObserver<ArrayList<TrendingRepo>>() {
                    @Override
                    public void onSuccess(ArrayList<TrendingRepo> trendingRepos) {
                        getMvpView().showContent(trendingRepos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }
}
