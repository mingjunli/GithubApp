package com.anly.githubapp.ui.module.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anly.githubapp.R;
import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.ui.base.BaseFragment;
import com.anly.githubapp.ui.module.repo.adapter.TrendingFragmentAdapter;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/7/19.
 */
public class TrendingContainerFragment extends BaseFragment {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private TrendingFragmentAdapter mAdapter;

    private static final Integer[] TRENDING_CATEGORY = {
            TrendingApi.LANG_JAVA,
            TrendingApi.LANG_PYTHON,
            TrendingApi.LANG_OC,
            TrendingApi.LANG_SWIFT,
            TrendingApi.LANG_BASH,
            TrendingApi.LANG_HTML
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending_container, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        getActivity().setTitle(R.string.menu_trending);

        mAdapter = new TrendingFragmentAdapter(getChildFragmentManager(), TRENDING_CATEGORY);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(TRENDING_CATEGORY.length);

        mTabs.setViewPager(mViewPager);
    }
}
