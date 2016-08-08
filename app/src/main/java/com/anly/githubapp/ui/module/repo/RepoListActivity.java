package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoListPresenter;
import com.anly.githubapp.ui.base.LceActivity;
import com.anly.githubapp.ui.module.repo.adapter.RepoListRecyclerAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/8/4.
 */
public class RepoListActivity extends LceActivity<ArrayList<Repo>> implements HasComponent<RepoComponent> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;

    private RepoListRecyclerAdapter mAdapter;

    @Inject
    RepoListPresenter mPresenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, RepoListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_repo_list);
        ButterKnife.bind(this);

        initViews();

        mPresenter.attachView(this);
        mPresenter.loadMyRepos();
    }

    private void initViews() {
        mAdapter = new RepoListRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);

        mRepoListView.setLayoutManager(new LinearLayoutManager(this));
        mRepoListView.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemtClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Repo repo = mAdapter.getItem(position);
            RepoDetailActivity.launch(RepoListActivity.this, repo.getOwner().getLogin(), repo.getName());
        }
    };

    @Override
    public void showContent(ArrayList<Repo> data) {
        super.showContent(data);
        AppLog.d("data size: " + data.size());
        mAdapter.setNewData(data);
    }

    @Override
    public View getAnchorView() {
        return null;
    }

    @Override
    public View.OnClickListener getRetryListener() {
        return null;
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
