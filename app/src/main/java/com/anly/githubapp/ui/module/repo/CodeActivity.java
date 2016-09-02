package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.repo.CodePresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.mvp.lce.LceView;
import com.pddstudio.highlightjs.HighlightJsView;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/9/2.
 */
public class CodeActivity extends BaseLoadingActivity implements LceView<Content>, HasComponent<RepoComponent> {

    private static final String EXTRA_OWNER = "extra_owner";
    private static final String EXTRA_REPO_NAME = "extra_repo_name";
    private static final String EXTRA_CODE_PATH = "extra_code";

    @BindView(R.id.code_view)
    HighlightJsView mCodeView;

    @Inject
    CodePresenter mPresenter;

    public static void launch(Context context, String owner, String repo, String path) {
        Intent intent = new Intent(context, CodeActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO_NAME, repo);
        intent.putExtra(EXTRA_CODE_PATH, path);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter.attachView(this);

        initViews();
        loadData();
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

    private void initViews() {
        mCodeView.setTheme(Theme.ANDROID_STUDIO);
        mCodeView.setHighlightLanguage(Language.AUTO_DETECT);
    }

    private void loadData() {
        String owner = getIntent().getStringExtra(EXTRA_OWNER);
        String repo = getIntent().getStringExtra(EXTRA_REPO_NAME);
        String path = getIntent().getStringExtra(EXTRA_CODE_PATH);

        mPresenter.getContentDetail(owner, repo, path);
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
    public RepoComponent getComponent() {
        return DaggerRepoComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .repoModule(new RepoModule())
                .build();
    }

    @Override
    public void showContent(Content data) {
        mCodeView.setSource(StringUtil.base64Decode(data.content));
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {

    }
}
