package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.presenter.main.MostStarPresenter;
import com.anly.githubapp.ui.base.BaseFragment;
import com.anly.githubapp.ui.module.main.adapter.RepoListRecyclerAdapter;
import com.anly.mvp.lce.LceView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/7/19.
 */
public class MostStarFragment extends BaseFragment implements LceView<ArrayList<Repo>> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;

    private RepoListRecyclerAdapter mAdapter;

    @Inject
    MostStarPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mPresenter.attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadRepoList("android", "java");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_star, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        mAdapter = new RepoListRecyclerAdapter(null);

        mRepoListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRepoListView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showContent(ArrayList<Repo> data) {
        AppLog.d("data size: " + data.size());
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {

    }


}
