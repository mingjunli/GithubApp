package com.anly.githubapp.ui.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.constant.IntentExtra;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerMainComponent;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.module.account.ProfileFragment;
import com.anly.githubapp.ui.module.repo.MostStarFragment;
import com.anly.githubapp.ui.module.repo.TrendingFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main Activity with Bottom Navigation Bar.
 */
public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.content_frame)
    FrameLayout mContentFrame;

    BottomBar mBottomBar;

    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomBar = BottomBar.attach(this, savedInstanceState);

        initViews();
    }

    private void initViews() {
        setSupportActionBar(mToolbar);

        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(mTabClickListener);
    }

    private OnMenuTabClickListener mTabClickListener = new OnMenuTabClickListener() {
        @Override
        public void onMenuTabSelected(@IdRes int menuItemId) {
            AppLog.d("onMenuTabSelected");
            switchMenu(getFragmentName(menuItemId));
        }

        @Override
        public void onMenuTabReSelected(@IdRes int menuItemId) {
            AppLog.d("onMenuTabReSelected");

        }
    };

    private Fragment mCurrentFragment;
    private void switchMenu(String fragmentName) {

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);

        if (fragment != null) {
            if (fragment == mCurrentFragment) return;

            mFragmentManager.beginTransaction().show(fragment).commit();
        } else {
            fragment = Fragment.instantiate(this, fragmentName);
            mFragmentManager.beginTransaction().add(R.id.content_frame, fragment, fragmentName).commit();
        }

        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }

        mCurrentFragment = fragment;
    }

    private String getFragmentName(int menuId) {
        switch (menuId) {
            case R.id.menu_trending:
                return TrendingFragment.class.getName();
            case R.id.menu_most_stars:
                return MostStarFragment.class.getName();
            case R.id.menu_account:
                return ProfileFragment.class.getName();

            default:
                return null;
        }
    }



    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    private long mLastBackTime = 0;

    @Override
    public void onBackPressed() {
        // finish while click back key 2 times during 1s.
        if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
            finish();
        } else {
            mLastBackTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.exit_click_back_again, Toast.LENGTH_SHORT).show();
        }

    }
}
