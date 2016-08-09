package com.anly.githubapp.ui.module.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.util.InputMethodUtils;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.AccountComponent;
import com.anly.githubapp.di.component.DaggerAccountComponent;
import com.anly.githubapp.di.module.AccountModule;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.presenter.account.LoginPresenter;
import com.anly.githubapp.ui.base.BaseLoadingActivity;
import com.anly.githubapp.ui.module.account.view.LoginView;
import com.anly.githubapp.ui.module.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseLoadingActivity implements LoginView, HasComponent<AccountComponent> {

    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login_btn)
    Button mLoginBtn;

    @Inject
    LoginPresenter mPresenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void launchForResult(Activity activity) {
        activity.startActivityForResult(new Intent(activity, LoginActivity.class), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setTitle(R.string.sign_in);

        mPresenter.attachView(this);
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @OnClick(R.id.login_btn)
    public void onClick() {

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            InputMethodUtils.hideSoftInput(this);
            mPresenter.login(username, password);
        }
    }

    @Override
    public AccountComponent getComponent() {
        return DaggerAccountComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .accountModule(new AccountModule())
                .build();
    }

    @Override
    public void loginSuccess(User user) {
        Snackbar.make(mLoginBtn, "Login Success", Snackbar.LENGTH_LONG).show();
        AccountPref.saveLogonUser(this, user);
        MainActivity.launch(this);
    }
}
