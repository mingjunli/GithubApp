package com.anly.githubapp.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.anly.githubapp.common.util.AppLog;
import com.anly.mvp.lce.LceView;

/**
 * Created by mingjun on 16/7/21.
 */
public abstract class LceActivity<M> extends BaseActivity implements LceView<M> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void showLoading() {
    }

    @Override
    public void dismissLoading() {
    }

    @Override
    public void showEmpty() {
    }

    @Override
    public void showError(Throwable e) {
    }

    @CallSuper
    @Override
    public void showContent(M data) {
    }

    public abstract View getAnchorView();
    public abstract View.OnClickListener getRetryListener();
}
