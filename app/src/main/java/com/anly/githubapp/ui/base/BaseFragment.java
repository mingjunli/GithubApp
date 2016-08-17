package com.anly.githubapp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.di.HasComponent;

/**
 * Created by mingjun on 16/7/16.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.d("onCreate:" + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLog.d("onDestroy:" + this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.d("onActivityCreated:" + this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLog.d("onViewCreated:" + this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.d("onCreateView:" + this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppLog.d("onDestroyView:" + this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppLog.d("onAttach:" + this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppLog.d("onDetach:" + this);
    }


    @Override
    public void onStart() {
        super.onStart();
        AppLog.d("onStart:" + this);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppLog.d("onResume:" + this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppLog.d("onPause:" + this);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppLog.d("onStop:" + this);
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
