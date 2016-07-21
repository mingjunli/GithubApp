package com.anly.githubapp.presenter.main;

import android.app.Activity;
import android.widget.EditText;

import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.rx.ResponseObserver;
import com.anly.githubapp.presenter.base.RxMvpPresenter;
import com.anly.mvp.lce.LceView;
import com.jakewharton.rxbinding.widget.RxTextSwitcher;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mingjun on 16/7/19.
 */
public class SearchPresenter extends RxMvpPresenter<LceView<ArrayList<Repo>>> {

    private final RepoApi mRepoApi;

    @Inject
    Activity mActivity;

    // search java as default
    private String mLanguage = "Java";

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    @Inject
    public SearchPresenter(RepoApi api) {
        this.mRepoApi = api;
    }

    public void bindSearchView(EditText searchTextView) {
        mCompositeSubscription.add(RxTextView.textChanges(searchTextView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() > 0;
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMap(new Func1<CharSequence, Observable<ArrayList<Repo>>>() {
                    @Override
                    public Observable<ArrayList<Repo>> call(CharSequence charSequence) {
                        String key = charSequence.toString();
                        return mRepoApi.searchMostStarredRepo(key, mLanguage)
                                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends ArrayList<Repo>>>() {
                                        @Override
                                        public Observable<? extends ArrayList<Repo>> call(Throwable throwable) {
                                            getMvpView().showError(throwable);
                                            AppLog.d("searchMostStarredRepo onErrorResumeNext:" + throwable);
                                            return Observable.empty();
                                        }
                                    })
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            mActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getMvpView().showLoading();
                                                }
                                            });
                                        }
                                    })
                                    .doOnTerminate(new Action0() {
                                        @Override
                                        public void call() {
                                            mActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getMvpView().dismissLoading();
                                                }
                                            });
                                        }
                                    });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ArrayList<Repo>>>() {
                    @Override
                    public Observable<? extends ArrayList<Repo>> call(Throwable throwable) {
                        AppLog.d("onErrorResumeNext:" + throwable);
                        getMvpView().showError(throwable);
                        return Observable.empty();
                    }
                })
                .subscribe(new ResponseObserver<ArrayList<Repo>>() {
                    @Override
                    public void onSuccess(ArrayList<Repo> repos) {
                        getMvpView().showContent(repos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.d("onError:" + e);
                        getMvpView().showError(e);
                    }
                }));

    }

    public void searchRepo(String key, String language) {
        mCompositeSubscription.add(mRepoApi.searchMostStarredRepo(key, language)
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
