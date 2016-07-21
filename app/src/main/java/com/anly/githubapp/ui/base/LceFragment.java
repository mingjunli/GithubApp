package com.anly.githubapp.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.anly.githubapp.ui.widget.loading.LoadingView;
import com.anly.mvp.lce.LceView;

/**
 * Created by mingjun on 16/7/21.
 */
public abstract class LceFragment<M> extends BaseFragment implements LceView<M> {

    private LoadingView mLoadingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingView = new LoadingView(this.getContext(), getLoadingMessage());
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingView.dismiss();
    }

    @NonNull
    public abstract String getLoadingMessage();
}
