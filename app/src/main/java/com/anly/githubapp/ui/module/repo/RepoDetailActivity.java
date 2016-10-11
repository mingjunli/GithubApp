package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoDetailPresenter;
import com.anly.githubapp.presenter.repo.StarActionPresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.module.account.UserActivity;
import com.anly.githubapp.ui.module.repo.adapter.ContributorListAdapter;
import com.anly.githubapp.ui.module.repo.adapter.ForkUserListAdapter;
import com.anly.githubapp.ui.module.repo.view.RepoDetailView;
import com.anly.githubapp.ui.widget.RepoItemView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepoDetailActivity extends BaseLoadingActivity implements RepoDetailView, HasComponent<RepoComponent> {

    @Inject
    RepoDetailPresenter mPresenter;
    @Inject
    StarActionPresenter mStarPresenter;

    @BindView(R.id.forks_count)
    TextView mForksCount;
    @BindView(R.id.contributors_count)
    TextView mContributorsCount;
    @BindView(R.id.fork_list)
    RecyclerView mForkListView;
    @BindView(R.id.contributor_list)
    RecyclerView mContributorListView;
    @BindView(R.id.repo_item_view)
    RepoItemView mRepoItemView;
    @BindView(R.id.contributor_layout)
    LinearLayout mContributorLayout;
    @BindView(R.id.fork_layout)
    LinearLayout mForkLayout;

    private ForkUserListAdapter mForkUserAdapter;
    private ContributorListAdapter mContributorAdapter;

    private static final String EXTRA_OWNER = "extra_owner";
    private static final String EXTRA_REPO_NAME = "extra_repo_name";

    private String mOwner;
    private String mRepoName;
    private RepoDetail mRepoDetail;

    public static void launch(Context context, String owner, String repoName) {
        Intent intent = new Intent(context, RepoDetailActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO_NAME, repoName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_repo_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViews();

        mPresenter.attachView(this);
        mStarPresenter.attachView(this);

        loadDetailData();
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mStarPresenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mForkListView.setLayoutManager(
                new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        mForkUserAdapter = new ForkUserListAdapter(null);
        mForkUserAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Repo repo = mForkUserAdapter.getItem(i);
                UserActivity.launch(RepoDetailActivity.this, repo.getOwner().getLogin());
            }
        });

        mForkListView.setAdapter(mForkUserAdapter);

        mContributorListView.setLayoutManager(
                new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        mContributorAdapter = new ContributorListAdapter(null);
        mContributorAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                User user = mContributorAdapter.getItem(i);
                UserActivity.launch(RepoDetailActivity.this, user.getLogin());
            }
        });

        mContributorListView.setAdapter(mContributorAdapter);
    }

    private void loadDetailData() {
        Intent intent = getIntent();
        if (intent != null) {
            mOwner = intent.getStringExtra(EXTRA_OWNER);
            mRepoName = intent.getStringExtra(EXTRA_REPO_NAME);

            if (!TextUtils.isEmpty(mOwner) && !TextUtils.isEmpty(mRepoName)) {
                mPresenter.loadRepoDetails(mOwner, mRepoName);
            }
        }
    }

    private void updateViews(RepoDetail detail) {
        mRepoDetail = detail;
        mOwner = detail.getBaseRepo().getOwner().getLogin();
        mRepoName = detail.getBaseRepo().getName();

        setTitle(mRepoName);

        mRepoItemView.setRepo(detail.getBaseRepo());
        mRepoItemView.setRepoActionListener(mRepoActionListener);

        int forks = detail.getBaseRepo().getForks_count();
        if (forks == 0) {
            mForkLayout.setVisibility(View.GONE);
        }
        else {
            mForkLayout.setVisibility(View.VISIBLE);
            mForksCount.setText(getResources().getString(R.string.forks_count, forks));
            mForkUserAdapter.setNewData(detail.getForks());
        }

        mContributorsCount.setText(getResources().getString(R.string.contributors_count, detail.getContributors().size()));
        mContributorAdapter.setNewData(detail.getContributors());
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
    public void showRepoDetail(RepoDetail detail) {
        updateViews(detail);
    }

    private RepoItemView.RepoActionListener mRepoActionListener = new RepoItemView.RepoActionListener() {
        @Override
        public void onStarAction(Repo repo) {
            if (AccountPref.checkLogon(RepoDetailActivity.this)) {
                mStarPresenter.starRepo(repo.getOwner().getLogin(), repo.getName());
            }
        }

        @Override
        public void onUnstarAction(Repo repo) {
            if (AccountPref.checkLogon(RepoDetailActivity.this)) {
                mStarPresenter.unstarRepo(repo.getOwner().getLogin(), repo.getName());
            }
        }

        @Override
        public void onUserAction(String username) {
            UserActivity.launch(RepoDetailActivity.this, username);
        }
    };

    @Override
    public void starSuccess() {
        Snackbar.make(mRepoItemView, "Star Success", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void starFailed() {
        Snackbar.make(mRepoItemView, "Star Failed", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void unstarSuccess() {
        Snackbar.make(mRepoItemView, "UnStar Success", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void unstarFailed() {
        Snackbar.make(mRepoItemView, "UnStar Failed", Snackbar.LENGTH_LONG).show();
    }

    @OnClick({R.id.code_layout, R.id.readme_layout, R.id.issues_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_layout:
                RepoTreeActivity.launch(this, mOwner, mRepoName);
                break;

            case R.id.readme_layout:
                ReadmeActivity.launch(this, mRepoDetail.getReadme());
                break;

            case R.id.issues_layout:
                break;
        }
    }
}
