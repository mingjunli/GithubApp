package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.RepoListPresenter;
import com.anly.githubapp.presenter.repo.UserListPresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.module.account.UserActivity;
import com.anly.githubapp.ui.module.repo.adapter.RepoListRecyclerAdapter;
import com.anly.githubapp.ui.module.repo.adapter.UserListRecyclerAdapter;
import com.anly.mvp.lce.LceView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/8/10.
 */
public class UserListActivity extends BaseLoadingActivity implements LceView<ArrayList<User>>, HasComponent<RepoComponent> {


    @BindView(R.id.user_list)
    RecyclerView mUserListView;

    @Inject
    UserListPresenter mPresenter;

    private UserListRecyclerAdapter mAdapter;

    private static final String EXTRA_USER_NAME = "extra_user_name";
    private static final String ACTION_FOLLOWING = "com.anly.githubapp.ACTION_FOLLOWING";
    private static final String ACTION_FOLLOWERS = "com.anly.githubapp.ACTION_FOLLOWERS";

    public static void launchToShowFollowing(Context context, String username) {
        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(EXTRA_USER_NAME, username);
        intent.setAction(ACTION_FOLLOWING);
        context.startActivity(intent);
    }

    public static void launchToShowFollowers(Context context, String username) {
        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(EXTRA_USER_NAME, username);
        intent.setAction(ACTION_FOLLOWERS);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        mPresenter.attachView(this);
        loadData();
    }

    private void loadData() {
        String action = getIntent().getAction();

        String username = getIntent().getStringExtra(EXTRA_USER_NAME);
        boolean isSelf = AccountPref.isSelf(this, username);

        if (ACTION_FOLLOWING.equals(action)) {
            setTitle(isSelf ? getString(R.string.my_following) : getString(R.string.following, username));
            mPresenter.loadUsers(username, isSelf, RepoApi.FOLLOWING);
        }
        else if (ACTION_FOLLOWERS.equals(action)) {
            setTitle(isSelf ? getString(R.string.my_followers) : getString(R.string.followers, username));
            mPresenter.loadUsers(username, isSelf, RepoApi.FOLLOWER);
        }
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
        mAdapter = new UserListRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);

        mUserListView.setLayoutManager(new LinearLayoutManager(this));
        mUserListView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.GRAY)
                .size(1)
                .build());

        mUserListView.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemtClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            User user = mAdapter.getItem(position);
            UserActivity.launch(UserListActivity.this, user.getLogin());
        }
    };

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public void showContent(ArrayList<User> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showEmpty() {

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
