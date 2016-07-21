package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.R;
import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.presenter.main.TrendingRepoPresenter;
import com.anly.githubapp.ui.base.BaseFragment;
import com.anly.githubapp.ui.base.LceFragment;
import com.anly.githubapp.ui.module.main.adapter.TrendingRepoRecyclerAdapter;
import com.anly.mvp.lce.LceView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/7/19.
 */
public class TrendingFragment extends LceFragment<ArrayList<TrendingRepo>> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;

    private TrendingRepoRecyclerAdapter mAdapter;

    @Inject
    TrendingRepoPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadTrendingRepo(TrendingApi.LANG_JAVA);
    }

    private void initViews() {
        mAdapter = new TrendingRepoRecyclerAdapter(null);

        mRepoListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRepoListView.setAdapter(mAdapter);
    }

    @NonNull
    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public void showContent(ArrayList<TrendingRepo> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {

    }
}
