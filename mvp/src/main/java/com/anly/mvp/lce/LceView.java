package com.anly.mvp.lce;

import android.support.annotation.UiThread;

import com.anly.mvp.MvpView;

/**
 * Created by mingjun on 16/7/5.
 */
public interface LceView<M> extends MvpView {

    @UiThread
    public void showLoading();

    @UiThread
    public void dismissLoading();

    @UiThread
    public void showContent(M data);

    @UiThread
    public void showError(Throwable e);

    @UiThread
    public void showEmpty();
}
