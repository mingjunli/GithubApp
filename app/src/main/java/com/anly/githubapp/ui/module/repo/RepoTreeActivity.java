package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.breadcrumb.LinearBreadcrumb;
import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoTreePresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.module.repo.adapter.RepoContentAdapter;
import com.anly.mvp.lce.LceView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/9/1.
 */
public class RepoTreeActivity extends BaseLoadingActivity implements LceView<ArrayList<Content>>, HasComponent<RepoComponent> {

    @BindView(R.id.repo_tree)
    RecyclerView mRepoTree;

    @BindView(R.id.path_view)
    LinearBreadcrumb mPathView;

    private RepoContentAdapter mAdapter;

    @Inject
    RepoTreePresenter mPresenter;

    private String mOwner;
    private String mRepoName;

    private static final String EXTRA_OWNER = "extra_owner";
    private static final String EXTRA_REPO_NAME = "extra_repo_name";
    public static void launch(Context context, String owner, String repoName) {
        Intent intent = new Intent(context, RepoTreeActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO_NAME, repoName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_repo_tree);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        mPresenter.attachView(this);

        mOwner = getIntent().getStringExtra(EXTRA_OWNER);
        mRepoName = getIntent().getStringExtra(EXTRA_REPO_NAME);

        setTitle(mOwner + "/" + mRepoName);

        loadContent(mOwner, mRepoName, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        mPathView.initRootCrumb();
        mPathView.setCallback(mPathSelectionCallback);

        mAdapter = new RepoContentAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);

        mRepoTree.setLayoutManager(new LinearLayoutManager(this));
        mRepoTree.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRepoTree.setAdapter(mAdapter);
    }

    private void loadContent(String owner, String repo, String path) {
        mPresenter.getRepoContents(owner, repo, path);
    }

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public RepoComponent getComponent() {
        return DaggerRepoComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .repoModule(new RepoModule())
                .build();
    }

    @Override
    public void showContent(ArrayList<Content> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {

    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            Content content = mAdapter.getItem(i);

            if (content.isDir()) {
                mPathView.addCrumb(new LinearBreadcrumb.Crumb(content.path, ""), true);
                loadContent(mOwner, mRepoName, content.path);
            }
            else if (content.isFile()) {
                // TODO
                CodeActivity.launch(RepoTreeActivity.this, mOwner, mRepoName, content.path);
            }
        }
    };

    private LinearBreadcrumb.SelectionCallback mPathSelectionCallback = new LinearBreadcrumb.SelectionCallback() {
        @Override
        public void onCrumbSelection(LinearBreadcrumb.Crumb crumb, String absolutePath, int count, int index) {

        }
    };
}
