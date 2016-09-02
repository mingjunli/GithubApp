package com.anly.githubapp.ui.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerMainComponent;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.module.repo.MostStarFragment;
import com.anly.githubapp.ui.module.repo.SearchActivity;
import com.anly.githubapp.ui.module.repo.TrendingContainerFragment;
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

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.d("trace===MainActivity onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomBar = BottomBar.attach(this, savedInstanceState);

        initViews();

        AppLog.d("trace===MainActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===MainActivity onResume");
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
            changeTitle(menuItemId);
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
                return TrendingContainerFragment.class.getName();
            case R.id.menu_most_stars:
                return MostStarFragment.class.getName();
            case R.id.menu_account:
                return MineFragment.class.getName();

            default:
                return null;
        }
    }

    private void changeTitle(int menuId) {
        switch (menuId) {
            case R.id.menu_trending:
                setTitle(R.string.menu_trending);
                break;

            case R.id.menu_most_stars:
                setTitle(R.string.menu_most_star);
                break;

            case R.id.menu_account:
                setTitle(R.string.menu_account);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mBottomBar.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                SearchActivity.launch(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
