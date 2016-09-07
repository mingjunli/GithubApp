package com.anly.githubapp.ui.module.repo.adapter;

import android.widget.ImageView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.wrapper.ImageLoader;
import com.anly.githubapp.data.model.User;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mingjun on 16/7/18.
 */
public class UserListRecyclerAdapter extends BaseQuickAdapter<User> {

    public UserListRecyclerAdapter(List<User> data) {
        super(R.layout.item_user_with_name, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, User user) {

        ImageView icon = holder.getView(R.id.user_icon);
        ImageLoader.loadWithCircle(user.getAvatar_url(), icon);

        holder.setText(R.id.username, user.getLogin());
    }
}
