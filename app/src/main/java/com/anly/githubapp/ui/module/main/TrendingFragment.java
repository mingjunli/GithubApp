package com.anly.githubapp.ui.module.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.presenter.main.TrendingRepoPresenter;
import com.anly.githubapp.ui.base.BaseLceFragment;
import com.anly.githubapp.ui.module.main.adapter.TrendingRepoRecyclerAdapter;
import com.anly.githubapp.ui.module.repo.RepoDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionMenu;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingjun on 16/7/19.
 */
public class TrendingFragment extends BaseLceFragment<ArrayList<TrendingRepo>> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;
    @BindView(R.id.menu)
    FloatingActionMenu mFloatMenu;

    private TrendingRepoRecyclerAdapter mAdapter;

    @Inject
    TrendingRepoPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_trending;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadTrendingRepo(TrendingApi.LANG_JAVA);
    }

    @Override
    public View.OnClickListener getRetryListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.loadTrendingRepo(TrendingApi.LANG_JAVA);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        mAdapter = new TrendingRepoRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));

        mRepoListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRepoListView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRepoListView.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            TrendingRepo trendingRepo = mAdapter.getItem(i);

            String fullName = StringUtil.replaceAllBlank(trendingRepo.getTitle());
            String[] array = fullName.split("\\/");
            RepoDetailActivity.launch(getActivity(), array[0], array[1]);
        }
    };

    @Override
    public void showContent(ArrayList<TrendingRepo> data) {
        super.showContent(data);
        mAdapter.setNewData(data);
    }

    @OnClick({R.id.lang_java,
            R.id.lang_oc,
            R.id.lang_swift,
            R.id.lang_bash,
            R.id.lang_html,
            R.id.lang_python})
    public void onLangMenuClick(View view) {
        mFloatMenu.close(true);

        int lang = TrendingApi.LANG_JAVA;

        switch (view.getId()) {
            case R.id.lang_java:
                lang = TrendingApi.LANG_JAVA;
                break;
            case R.id.lang_oc:
                lang = TrendingApi.LANG_OC;
                break;
            case R.id.lang_swift:
                lang = TrendingApi.LANG_SWIFT;
                break;
            case R.id.lang_bash:
                lang = TrendingApi.LANG_BASH;
                break;
            case R.id.lang_html:
                lang = TrendingApi.LANG_HTML;
                break;
            case R.id.lang_python:
                lang = TrendingApi.LANG_PYTHON;
                break;
        }

        mPresenter.loadTrendingRepo(lang);
    }
}
