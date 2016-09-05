package com.anly.githubapp.ui.module.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.account.UserPresenter;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.widget.UserCard;
import com.anly.mvp.lce.LceView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingjun on 16/8/17.
 */
public class UserActivity extends BaseLoadingActivity implements LceView<User>, HasComponent<RepoComponent> {

    @BindView(R.id.user_card)
    UserCard mUserCard;

    @Inject
    UserPresenter mPresenter;

    private static final String EXTRA_USER_NAME = "extra_user_name";
    private static final String EXTRA_USER = "extra_user";
    public static void launch(Context context, String name) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_USER_NAME, name);
        context.startActivity(intent);
    }

    public static void launch(Context context, User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter.attachView(this);

        loadUser();
    }

    private void loadUser() {
        User user = getIntent().getParcelableExtra(EXTRA_USER);
        if (user != null) {
            setTitle(user.getLogin());
            mUserCard.setUser(user);
        }
        else {
            String name = getIntent().getStringExtra(EXTRA_USER_NAME);
            setTitle(name);
            mPresenter.getSingleUser(name);
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

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @OnClick({R.id.repo_layout, R.id.starred_layout, R.id.following_layout, R.id.followers_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.repo_layout:
                break;
            case R.id.starred_layout:
                break;
            case R.id.following_layout:
                break;
            case R.id.followers_layout:
                break;
        }
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
    public void showContent(User data) {
        mUserCard.setUser(data);
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {

    }
}
