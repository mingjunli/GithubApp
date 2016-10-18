package com.anly.githubapp.ui.module.repo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.presenter.main.TrendingRepoPresenter;
import com.anly.githubapp.ui.base.BaseFragment;
import com.anly.githubapp.ui.module.repo.adapter.TrendingRepoRecyclerAdapter;
import com.anly.mvp.lce.LceView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/7/19.
 */
public class TrendingFragment extends BaseFragment implements LceView<ArrayList<TrendingRepo>> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private TrendingRepoRecyclerAdapter mAdapter;

    @Inject
    TrendingRepoPresenter mPresenter;

    private String mCurrentLang = "java";

    private static final String EXTRA_LANG = "extra_lang";
    public static TrendingFragment newInstance(String lang) {

        Bundle args = new Bundle();
        args.putString(EXTRA_LANG, lang);

        TrendingFragment fragment = new TrendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mCurrentLang = getArguments().getString(EXTRA_LANG);
        mPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // default daily
        // TODO
        mPresenter.loadTrendingRepo(mCurrentLang, "daily");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(mRefreshListener);

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
            RepoDetailActivity.launch(getActivity(), trendingRepo.owner, trendingRepo.name);
        }
    };

    @Override
    public void showLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismissLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContent(ArrayList<TrendingRepo> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            mAdapter.setNewData(data);
        }
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {
        // TODO
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            AppLog.d("onRefresh, mCurrentLang:" + mCurrentLang);
            mPresenter.loadTrendingRepo(mCurrentLang, "daily");
        }
    };
}
