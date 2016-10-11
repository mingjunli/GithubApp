package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.anly.githubapp.R;
import com.anly.githubapp.data.model.Issue;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.mvp.lce.LceView;

import java.util.List;

/**
 * Created by mingjun on 16/10/11.
 */

public class IssueListActivity extends BaseLoadingActivity implements LceView<List<Issue>> {

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
    }

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public void showContent(List<Issue> data) {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showEmpty() {

    }
}
