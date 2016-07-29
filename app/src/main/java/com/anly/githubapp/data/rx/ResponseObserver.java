package com.anly.githubapp.data.rx;

import com.anly.githubapp.common.util.AppLog;

import rx.Subscriber;

/**
 * Created by mingjun on 16/6/28.
 */
public abstract class ResponseObserver<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        AppLog.d("onCompleted");
    }

    @Override
    public void onNext(T t) {
        AppLog.d("onNext");
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
}
