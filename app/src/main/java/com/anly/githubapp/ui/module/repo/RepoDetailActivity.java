package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.common.util.ImageLoader;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoDetailPresenter;
import com.anly.githubapp.presenter.repo.StarActionPresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.module.repo.adapter.ContributorListAdapter;
import com.anly.githubapp.ui.module.repo.adapter.ForkUserListAdapter;
import com.anly.githubapp.ui.module.repo.view.RepoDetailView;
import com.zzhoujay.richtext.RichText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepoDetailActivity extends BaseLoadingActivity implements RepoDetailView, HasComponent<RepoComponent> {

    @Inject
    RepoDetailPresenter mPresenter;
    @Inject
    StarActionPresenter mStarPresenter;

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

    private String mOwner;
    private String mRepo;
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
        ButterKnife.bind(this);
        initViews();
        mPresenter.attachView(this);
        mStarPresenter.attachView(this);
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            mOwner = intent.getStringExtra(EXTRA_OWNER);
            mRepo = intent.getStringExtra(EXTRA_REPO_NAME);

            if (!TextUtils.isEmpty(mOwner) && !TextUtils.isEmpty(mRepo)) {
                mPresenter.loadRepoDetails(mOwner, mRepo);
            }
        }
    }

    private void updateViews(RepoDetail detail) {
        mRepoDetail = detail;
        mOwner = detail.getBaseRepo().getOwner().getLogin();
        mRepo = detail.getBaseRepo().getName();

        setTitle(mRepo);

        mDesc.setText(detail.getBaseRepo().getDescription());
        ImageLoader.load(this, detail.getBaseRepo().getOwner().getAvatar_url(), mOwnerIcon);
        mOwnerName.setText(mOwner);

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

    @Override
    public void showRepoDetail(RepoDetail detail) {
        updateViews(detail);
    }

    @Override
    public void starSuccess() {
        Snackbar.make(mStars, "Star Success", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void starFailed() {
        Snackbar.make(mStars, "Star Failed", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.star_view)
    public void onStarViewClicked() {
        AppLog.d("onStarViewClicked, owner:" + mOwner + ", repo:" + mRepo);
        mStarPresenter.starRepo(mOwner, mRepo);
    }
}
