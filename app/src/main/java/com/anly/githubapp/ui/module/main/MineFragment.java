package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.FeedbackPlatform;
import com.anly.githubapp.common.wrapper.ImageLoader;
import com.anly.githubapp.common.wrapper.SharePlatform;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.ui.base.BaseFragment;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.anly.githubapp.ui.module.account.ProfileActivity;
import com.anly.githubapp.ui.module.account.UserActivity;
import com.anly.githubapp.ui.module.setting.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingjun on 16/8/16.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.user_icon)
    ImageView mUserIcon;
    @BindView(R.id.username)
    TextView mUsername;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, contentView);

        getActivity().setTitle(R.string.menu_account);

        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUser();
    }

    private void updateUser() {
        if (AccountPref.isLogon(getContext())) {
            User user = AccountPref.getLogonUser(getContext());
            ImageLoader.loadWithCircle(getContext(), user.getAvatar_url(), mUserIcon);
            String displayName = TextUtils.isEmpty(user.getName()) ? user.getLogin() : user.getName();
            mUsername.setText(displayName);
        }
        else {
            mUsername.setText(R.string.please_login);
        }
    }

    @OnClick({R.id.account_view, R.id.history, R.id.share_app, R.id.feedback, R.id.settings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_view:
                if (AccountPref.isLogon(getContext())) {
                    UserActivity.launch(getActivity(), AccountPref.getLogonUser(getActivity()));
                }
                else {
                    LoginActivity.launch(getActivity());
                }
                break;
            case R.id.history:
                // TODO
                break;

            case R.id.share_app:
                SharePlatform.share(getActivity());
                break;

            case R.id.feedback:
                FeedbackPlatform.openFeedback(getActivity());
                break;

            case R.id.settings:
                SettingsActivity.launch(getActivity());
                break;
        }
    }
}
