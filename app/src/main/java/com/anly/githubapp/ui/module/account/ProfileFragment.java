package com.anly.githubapp.ui.module.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.ImageLoader;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/8/8.
 */
public class ProfileFragment extends BaseFragment {

    @BindView(R.id.user_icon)
    ImageView mUserIcon;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.bio)
    TextView mBio;

    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = AccountPref.getLogonUser(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_profile, null);
        ButterKnife.bind(this, contentView);

        updateUser(mUser);
        return contentView;
    }

    private void updateUser(User user) {
        ImageLoader.loadWithCircle(getContext(), user.getAvatar_url(), mUserIcon);

        String displayName = TextUtils.isEmpty(user.getName()) ?  user.getLogin() : user.getName();
        mUsername.setText(displayName);

        mBio.setText(user.getBio());
    }
}
