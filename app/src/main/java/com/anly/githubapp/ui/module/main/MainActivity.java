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
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerMainComponent;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // save our header or result
    private Drawer mDrawer = null;
    private AccountHeader mAccountHeader = null;
    private ProfileDrawerItem mAccountProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = mDrawer.saveInstanceState(outState);
        outState = mAccountHeader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    private void initViews(Bundle savedInstanceState) {

        setSupportActionBar(mToolbar);

        ArrayList<IDrawerItem> items = new ArrayList<>();
        for (final MainMenuConfig.MainMenu menu : MainMenuConfig.MENUS) {
            items.add(new PrimaryDrawerItem()
                    .withName(menu.labelResId)
                    .withIcon(menu.iconResId)
                    .withSelectable(true)
                    .withIdentifier(menu.id)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            switchMenu(menu);
                            return false;
                        }
                    })
            );
        }

        mAccountProfile = new ProfileDrawerItem()
                .withName("Please Login")
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
                        LoginActivity.launchForResult(MainActivity.this);
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
                .withDrawerItems(items)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        // default selected the TREND.
        mDrawer.setSelection(MainMenuConfig.ID_TREND, true);

        if (AccountPref.getLogonUser(this) != null) {
            updateUserInfo(AccountPref.getLogonUser(this));
        }
    }

    private Fragment mCurrentFragment;
    private void switchMenu(MainMenuConfig.MainMenu menu) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        String tag = getString(menu.labelResId);
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        AppLog.d("switch menu:" + fragment);
        if (fragment != null) {
            if (fragment == mCurrentFragment) return;

            fragmentManager.beginTransaction().show(fragment).commit();
        }
        else {
            fragment = Fragment.instantiate(this, menu.fragmentClass);
            fragmentManager.beginTransaction().add(R.id.content_frame, fragment, tag).commit();
        }

        if (mCurrentFragment != null) {
            fragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }

        mCurrentFragment = fragment;
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
