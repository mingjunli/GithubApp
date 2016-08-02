package com.anly.githubapp.ui.module.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.constant.IntentExtra;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.AccountComponent;
import com.anly.githubapp.di.component.DaggerAccountComponent;
import com.anly.githubapp.di.module.AccountModule;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.presenter.account.LoginPresenter;
import com.anly.githubapp.ui.base.LceActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends LceActivity<User> implements HasComponent<AccountComponent> {

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

        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showContent(User data) {
        super.showContent(data);
        Intent result = new Intent();
        result.putExtra(IntentExtra.USER, data);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public View getAnchorView() {
        return null;
    }

    @Override
    public View.OnClickListener getRetryListener() {
        return null;
    }

    @OnClick(R.id.login_btn)
    public void onClick() {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
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
}
