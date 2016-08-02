package com.anly.mvp.lce;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.anly.mvp.R;

/**
 * Created by mingjun on 16/8/2.
 */
public class LceHelper {

    private Context mContext;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    private View mDecorAnchor;

    public LceHelper(Activity activity) {
        this.mContext = activity;

        mDecorAnchor = activity.getWindow().getDecorView();

        mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_view, null);
        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_view, null);
        mErrorView = LayoutInflater.from(mContext).inflate(R.layout.error_view, null);
    }

    public void loading() {
        loading(mDecorAnchor, true);
    }

    public void loading(View anchor, boolean isCover) {
        addView(mLoadingView, anchor, isCover);
    }

    public void dismiss() {
        removeView(mLoadingView);
    }

    public void dismissAll() {
        removeView(mLoadingView);
        removeView(mEmptyView);
        removeView(mErrorView);
    }

    public void error() {
        error(mDecorAnchor, null);
    }

    public void error(View.OnClickListener retryListener) {
        error(mDecorAnchor, retryListener);
    }

    public void error(View anchor, View.OnClickListener retryListener) {
        removeView(mLoadingView);
        removeView(mEmptyView);
        addView(mErrorView, anchor, true);

        if (retryListener != null) {
            mErrorView.findViewById(R.id.retry_btn).setOnClickListener(retryListener);
        }
    }

    public void empty() {
        empty(mDecorAnchor);
    }

    public void empty(View anchor) {
        removeView(mLoadingView);
        removeView(mErrorView);
        addView(mEmptyView, anchor, true);
    }

    private void addView(View view, View anchor, boolean isCover) {
        if (anchor == null) throw new NullPointerException("anchor view should not be null!!!");

        if (anchor instanceof ViewGroup) {
            ViewGroup rootView = (ViewGroup) anchor;

            rootView.removeView(view);

            ViewGroup.LayoutParams lp;

            if (isCover) {
                lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setBackgroundDrawable(rootView.getBackground());
            } else {
                lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            rootView.addView(view, lp);
        }
    }

    private void removeView(View view) {
        if (view != null && view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }
}
