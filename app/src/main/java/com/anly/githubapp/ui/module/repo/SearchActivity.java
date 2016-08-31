package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.IconicUtil;
import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.common.util.InputMethodUtils;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerRepoComponent;
import com.anly.githubapp.di.component.RepoComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.presenter.main.SearchPresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.module.repo.adapter.RepoListRecyclerAdapter;
import com.anly.githubapp.ui.module.repo.view.SearchView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.devicon_typeface_library.DevIcon;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/8/8.
 */
public class SearchActivity extends BaseLoadingActivity implements SearchView<ArrayList<Repo>>, HasComponent<RepoComponent> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    private Drawer mDrawer;

    @Inject
    SearchPresenter mPresenter;

    private RepoListRecyclerAdapter mAdapter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mPresenter.attachView(this);
        initViews();
    }

    @Override
    public String getLoadingMessage() {
        return getString(R.string.load_searching);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.search);

        mSearchView.setVoiceSearch(false);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setOnQueryTextListener(mQueryListener);
        mSearchView.post(new Runnable() {
            @Override
            public void run() {
                mSearchView.showSearch(false);
            }
        });

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerWidthRes(R.dimen.dimen_180)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Java").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_java_plain)),
                        new PrimaryDrawerItem().withName("Objective-C").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_apple_plain)),
                        new PrimaryDrawerItem().withName("Swift").withIcon(R.drawable.ic_swift),
                        new PrimaryDrawerItem().withName("JavaScript").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_javascript_plain)),
                        new PrimaryDrawerItem().withName("Python").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_python_plain)),
                        new PrimaryDrawerItem().withName("HTML").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_html5_plain)),
                        new PrimaryDrawerItem().withName("C#").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_csharp_plain_wordmark)),
                        new PrimaryDrawerItem().withName("C++").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_cplusplus_plain_wordmark)),
                        new PrimaryDrawerItem().withName("Ruby").withIcon(IconicUtil.getSmallIcon(this, DevIcon.Icon.dev_ruby_plain))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        AppLog.d("onItemClick, position = " + position + ", item = " + ((Nameable)drawerItem).getName());

                        mCurrentLang = ((Nameable)drawerItem).getName().toString();
                        search(mCurrentKey, mCurrentLang);
                        mDrawer.closeDrawer();
                        return true;
                    }
                })
                .build();

        mAdapter = new RepoListRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);

        mRepoListView.setLayoutManager(new LinearLayoutManager(this));
        mRepoListView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRepoListView.setAdapter(mAdapter);

        // default is null
        mCurrentLang = "";
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemtClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Repo repo = mAdapter.getItem(position);
            RepoDetailActivity.launch(SearchActivity.this, repo.getOwner().getLogin(), repo.getName());
        }
    };

    private MaterialSearchView.OnQueryTextListener mQueryListener = new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            InputMethodUtils.hideSoftInput(SearchActivity.this);
            mSearchView.closeSearch();

            mCurrentKey = query;
            search(mCurrentKey, mCurrentLang);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private String mCurrentKey;
    private String mCurrentLang;
    private void search(String key, String lang) {
        AppLog.d("search, key = " + key + ", lang = " + lang);
        if (!TextUtils.isEmpty(key)) {
            mPresenter.searchRepo(key, lang);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
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
    public void showSearchResult(ArrayList<Repo> repos) {
        mAdapter.setNewData(repos);
    }
}
