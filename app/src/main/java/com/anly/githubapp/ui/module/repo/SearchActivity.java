package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.common.util.CrossfaderWrapper;
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
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
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
        return null;
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchView.setVoiceSearch(false);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setOnQueryTextListener(mQueryListener);
        mSearchView.showSearch(false);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withSliderBackgroundColor(getResources().getColor(R.color.md_teal_A100))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("java").withIcon(R.drawable.ic_github),
                        new PrimaryDrawerItem().withName("android").withIcon(R.drawable.ic_github)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        AppLog.d("onItemClick, position = " + position + ", item = " + ((Nameable)drawerItem).getName());
                        return true;
                    }
                })
                .withGenerateMiniDrawer(true)
                .buildView();

        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
        MiniDrawer miniDrawer = mDrawer.getMiniDrawer();
        View miniDrawerView = miniDrawer.build(this);
        miniDrawerView.setBackgroundColor(getResources().getColor(R.color.md_teal_A100));

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
        Crossfader crossFader = new Crossfader()
                .withContent(findViewById(R.id.repo_list))
                .withFirst(mDrawer.getSlider(), getResources().getDimensionPixelOffset(R.dimen.dimen_120))
                .withSecond(miniDrawerView, getResources().getDimensionPixelOffset(R.dimen.dimen_72))
                .build();

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniDrawer.withCrossFader(new CrossfaderWrapper(crossFader));

        mAdapter = new RepoListRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);

        mRepoListView.setLayoutManager(new LinearLayoutManager(this));
        mRepoListView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRepoListView.setAdapter(mAdapter);
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

            PrimaryDrawerItem current = (PrimaryDrawerItem) mDrawer.getDrawerItems().get(mDrawer.getCurrentSelectedPosition());
            AppLog.d("current:" + current.getName().toString());

            mPresenter.searchRepo(query, current.getName().toString());
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

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
        mSearchView.closeSearch();
        mAdapter.setNewData(repos);
    }
}
