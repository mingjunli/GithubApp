package com.anly.githubapp.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.anly.githubapp.common.util.AppLog;
import com.anly.mvp.lce.LceHelper;
import com.anly.mvp.lce.LceView;

/**
 * Created by mingjun on 16/7/21.
 */
public abstract class LceFragment<M> extends BaseFragment implements LceView<M> {

    private LceHelper mLceHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLceHelper = new LceHelper(getActivity());
    }

    @Override
    public void showLoading() {
        if (getAnchorView() != null) {
            mLceHelper.loading(getAnchorView(), true);
        }
        else {
            mLceHelper.loading();
        }
    }

    @Override
    public void dismissLoading() {
        mLceHelper.dismiss();
    }

    @Override
    public void showEmpty() {
        if (getAnchorView() != null) {
            mLceHelper.empty(getAnchorView());
        }
        else {
            mLceHelper.empty();
        }
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
        if (getAnchorView() != null) {
            mLceHelper.error(getAnchorView(), getRetryListener());
        }
        else {
            mLceHelper.error(getRetryListener());
        }
    }

    @CallSuper
    @Override
    public void showContent(M data) {
        mLceHelper.dismissAll();
    }

    public abstract View getAnchorView();
    public abstract View.OnClickListener getRetryListener();
}
