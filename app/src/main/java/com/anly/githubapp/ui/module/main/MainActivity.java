package com.anly.githubapp.ui.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.config.MainMenuConfig;
import com.anly.githubapp.common.constant.IntentExtra;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.api.AccountApi;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerMainComponent;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.anly.githubapp.ui.module.repo.RepoListActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // save our header or result
    private Drawer mDrawer = null;
    private AccountHeader mAccountHeader = null;
    private ProfileDrawerItem mAccountProfile;

    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        setSupportActionBar(mToolbar);

        mAccountProfile = new ProfileDrawerItem()
                .withName(getString(R.string.please_login))
                .withIcon(R.drawable.ic_github);

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabled(false)
                .addProfiles(
                        mAccountProfile
                )
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        AppLog.d("already log on:" + AccountPref.isLogon(MainActivity.this));

                        if (AccountPref.isLogon(MainActivity.this)) {
                            RepoListActivity.launch(MainActivity.this);
                        }
                        else {
                            LoginActivity.launchForResult(MainActivity.this);
                        }
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        mDrawer = new DrawerBuilder(this)
                .withActivity(this)
                .withRootView(R.id.drawer_container)
                .withToolbar(mToolbar)
                .withDisplayBelowStatusBar(true)
                .withPositionBasedStateManagement(false)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.menu_trending)
                                .withIcon(R.drawable.ic_trending_up)
                                .withIdentifier(MainMenuConfig.ID_TREND)
                                .withTag(TrendingFragment.class.getName()),
                        new PrimaryDrawerItem()
                                .withName(R.string.menu_most_star)
                                .withIcon(R.drawable.ic_star)
                                .withIdentifier(MainMenuConfig.ID_STARS)
                                .withTag(MostStarFragment.class.getName()),
                        new PrimaryDrawerItem()
                                .withName(R.string.menu_search)
                                .withIcon(R.drawable.ic_search)
                                .withIdentifier(MainMenuConfig.ID_SEARCH)
                                .withTag(SearchFragment.class.getName())
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null && drawerItem instanceof PrimaryDrawerItem) {
                            switchMenu((PrimaryDrawerItem) drawerItem);
                        }
                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();

        mDrawer.setSelection(MainMenuConfig.ID_TREND, true);

        if (AccountPref.getLogonUser(this) != null) {
            updateUserInfo(AccountPref.getLogonUser(this));
        }
    }

    private Fragment mCurrentFragment;
    private void switchMenu(PrimaryDrawerItem item) {
        String tag = item.getName().getText(this);
        AppLog.d("switch menu tag:" + tag);

        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        AppLog.d("switch menu fragment:" + fragment);

        if (fragment != null) {
            if (fragment == mCurrentFragment) return;

            mFragmentManager.beginTransaction().show(fragment).commit();
        }
        else {
            fragment = Fragment.instantiate(this, (String) item.getTag());
            mFragmentManager.beginTransaction().add(R.id.content_frame, fragment, tag).commit();
        }

        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }

        mCurrentFragment = fragment;
        setTitle(tag);
    }

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode) {
            User user = data.getParcelableExtra(IntentExtra.USER);
            updateUserInfo(user);
        }
    }

    private void updateUserInfo(User user) {
        String displayName = TextUtils.isEmpty(user.getName()) ? user.getLogin() : user.getName();
        mAccountProfile.withName(displayName)
                .withIcon(user.getAvatar_url())
                .withEmail(user.getEmail());

        mAccountHeader.updateProfile(mAccountProfile);
    }
}
