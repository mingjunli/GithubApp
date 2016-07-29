package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.common.util.ImageLoader;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoDetailPresenter;
import com.anly.githubapp.ui.base.LceActivity;
import com.anly.githubapp.ui.module.repo.adapter.ContributorListAdapter;
import com.anly.githubapp.ui.module.repo.adapter.ForkUserListAdapter;
import com.zzhoujay.richtext.RichText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoDetailActivity extends LceActivity<RepoDetail> implements HasComponent<RepoComponent> {

    @Inject
    RepoDetailPresenter mPresenter;

    @BindView(R.id.desc)
    TextView mDesc;
    @BindView(R.id.image)
    ImageView mOwnerIcon;
    @BindView(R.id.owner)
    TextView mOwnerName;
    @BindView(R.id.watch)
    TextView mWatch;
    @BindView(R.id.star)
    TextView mStars;
    @BindView(R.id.fork)
    TextView mFork;
    @BindView(R.id.forks_count)
    TextView mForksCount;
    @BindView(R.id.contributors_count)
    TextView mContributorsCount;
    @BindView(R.id.readme)
    TextView mReadme;
    @BindView(R.id.fork_list)
    RecyclerView mForkListView;
    @BindView(R.id.contributor_list)
    RecyclerView mContributorListView;

    private ForkUserListAdapter mForkUserAdapter;
    private ContributorListAdapter mContributorAdapter;

    private static final String EXTRA_OWNER = "extra_owner";
    private static final String EXTRA_REPO_NAME = "extra_repo_name";

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
        ButterKnife.bind(this);
        initViews();
        mPresenter.attachView(this);

        loadDetailData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {

        mForkListView.setLayoutManager(
                new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        mForkUserAdapter = new ForkUserListAdapter(null);
        mForkListView.setAdapter(mForkUserAdapter);

        mContributorListView.setLayoutManager(
                new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        mContributorAdapter = new ContributorListAdapter(null);
        mContributorListView.setAdapter(mContributorAdapter);
    }

    private void loadDetailData() {
        Intent intent = getIntent();
        if (intent != null) {
            String owner = intent.getStringExtra(EXTRA_OWNER);
            String repo = intent.getStringExtra(EXTRA_REPO_NAME);

            if (!TextUtils.isEmpty(owner) && !TextUtils.isEmpty(repo)) {
                mPresenter.loadRepoDetails(owner, repo);
            }
        }
    }

    @NonNull
    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public void showContent(RepoDetail data) {
        updateViews(data);
    }

    @Override
    public void showError(Throwable e) {

    }

    private void updateViews(RepoDetail detail) {
        setTitle(detail.getBaseRepo().getName());

        mDesc.setText(detail.getBaseRepo().getDescription());
        ImageLoader.load(this, detail.getBaseRepo().getOwner().getAvatar_url(), mOwnerIcon);
        mOwnerName.setText(detail.getBaseRepo().getOwner().getLogin());

        mStars.setText(String.valueOf(detail.getBaseRepo().getStargazers_count()));
        mWatch.setText(String.valueOf(detail.getBaseRepo().getWatchers_count()));
        mFork.setText(String.valueOf(detail.getBaseRepo().getForks_count()));

        mForksCount.setText(String.valueOf(detail.getBaseRepo().getForks_count()));
        mForkUserAdapter.setNewData(detail.getForks());

        mContributorsCount.setText(String.valueOf(detail.getContributors().size()));
        mContributorAdapter.setNewData(detail.getContributors());

        RichText.fromMarkdown(detail.getReadme().content)
                .async(true)
                .into(mReadme);
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
