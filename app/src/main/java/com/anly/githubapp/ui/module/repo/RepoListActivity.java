package com.anly.githubapp.ui.module.repo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoListPresenter;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.module.main.adapter.RepoListRecyclerAdapter;
import com.anly.mvp.lce.LceView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListActivity extends BaseActivity implements LceView<ArrayList<Repo>>, HasComponent<RepoComponent>{

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;

    @Inject
    RepoListPresenter mRepoListPresenter;

    private RepoListRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        setContentView(R.layout.activity_repo_list);
        ButterKnife.bind(this);

        mRepoListPresenter.attachView(this);

        mAdapter = new RepoListRecyclerAdapter(null);
        mRepoListView.setLayoutManager(new LinearLayoutManager(this));
        mRepoListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRepoListPresenter.loadRepoList("android", "java");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRepoListPresenter.detachView();
    }

    @Override
    public void showLoading() {
        mAdapter.openLoadAnimation();
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showContent(ArrayList<Repo> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public RepoComponent getComponent() {
        return DaggerRepoComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .repoModule(new RepoModule())
                .build();
    }
}
