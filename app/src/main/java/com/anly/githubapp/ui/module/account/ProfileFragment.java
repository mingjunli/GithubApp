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
import com.anly.githubapp.ui.module.repo.RepoListActivity;
import com.anly.githubapp.ui.module.repo.StarredRepoListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.company)
    TextView mCompany;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.blog)
    TextView mBlog;
    @BindView(R.id.email)
    TextView mEmail;

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

        getActivity().setTitle(R.string.menu_account);

        updateUser(mUser);
        return contentView;
    }

    private void updateUser(User user) {
        ImageLoader.loadWithCircle(getContext(), user.getAvatar_url(), mUserIcon);

        String displayName = TextUtils.isEmpty(user.getName()) ? user.getLogin() : user.getName();
        mUsername.setText(displayName);

        if (TextUtils.isEmpty(user.getBio())) {
            mBio.setVisibility(View.GONE);
        }
        else {
            mBio.setVisibility(View.VISIBLE);
            mBio.setText(user.getBio());
        }

        if (TextUtils.isEmpty(user.getCompany())) {
            mCompany.setVisibility(View.GONE);
        }
        else {
            mCompany.setVisibility(View.VISIBLE);
            mCompany.setText(user.getCompany());
        }

        if (TextUtils.isEmpty(user.getBlog())) {
            mBlog.setVisibility(View.GONE);
        }
        else {
            mBlog.setVisibility(View.VISIBLE);
            mBlog.setText(user.getBlog());
        }

        if (TextUtils.isEmpty(user.getLocation())) {
            mLocation.setVisibility(View.GONE);
        }
        else {
            mLocation.setVisibility(View.VISIBLE);
            mLocation.setText(user.getLocation());
        }

        if (TextUtils.isEmpty(user.getEmail())) {
            mEmail.setVisibility(View.GONE);
        }
        else {
            mEmail.setVisibility(View.VISIBLE);
            mEmail.setText(user.getEmail());
        }
    }

    @OnClick({R.id.my_repo, R.id.my_starred})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_repo:
                RepoListActivity.launch(getActivity());
                break;
            case R.id.my_starred:
                StarredRepoListActivity.launch(getActivity());
                break;
        }
    }
}
