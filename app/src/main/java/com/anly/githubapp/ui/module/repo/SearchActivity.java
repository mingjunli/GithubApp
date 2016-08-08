package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/8/8.
 */
public class SearchActivity extends BaseActivity implements HasComponent<RepoComponent> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
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
