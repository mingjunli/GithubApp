package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.anly.githubapp.R;
import com.anly.githubapp.common.config.MainMenuConfig;
import com.anly.githubapp.ui.base.BaseActivity;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.nav_bar)
    NavigationTabBar mNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mNavBar.setModels(MainMenuConfig.getNavModels());
    }
}
