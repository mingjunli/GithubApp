package com.anly.githubapp.ui.module.repo.adapter;

import android.widget.ImageView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.ImageLoader;
import com.anly.githubapp.data.model.User;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mingjun on 16/7/29.
 */
public class ContributorListAdapter extends BaseQuickAdapter<User> {

    public ContributorListAdapter(List<User> data) {
        super(R.layout.item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, User user) {
        ImageLoader.load(
                user.getAvatar_url(), (ImageView) holder.getView(R.id.user_icon));
    }
}
