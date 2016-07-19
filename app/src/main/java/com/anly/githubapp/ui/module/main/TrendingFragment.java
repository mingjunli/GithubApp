package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.R;
import com.anly.githubapp.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/7/19.
 */
public class TrendingFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {

    }
}
