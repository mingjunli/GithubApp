package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.presenter.main.TrendingRepoPresenter;
import com.anly.githubapp.ui.base.LceFragment;
import com.anly.githubapp.ui.module.main.adapter.TrendingRepoRecyclerAdapter;
import com.anly.githubapp.ui.module.repo.RepoDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/7/19.
 */
public class TrendingFragment extends LceFragment<ArrayList<TrendingRepo>> {

    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;
    @BindView(R.id.root_layout)
    LinearLayout mRootLayout;

    private TrendingRepoRecyclerAdapter mAdapter;

    @Inject
    TrendingRepoPresenter mPresenter;

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

        mPresenter.attachView(this);
        mPresenter.loadTrendingRepo(TrendingApi.LANG_JAVA);
    }

    @Override
    public View getAnchorView() {
        return mRootLayout;
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

        mRepoListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
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
}
