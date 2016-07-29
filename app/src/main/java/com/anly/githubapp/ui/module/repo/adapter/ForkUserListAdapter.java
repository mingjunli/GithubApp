package com.anly.githubapp.ui.module.repo.adapter;

import android.widget.ImageView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.ImageLoader;
import com.anly.githubapp.data.model.Repo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mingjun on 16/7/29.
 */
public class ForkUserListAdapter extends BaseQuickAdapter<Repo> {

    public ForkUserListAdapter(List<Repo> data) {
        super(R.layout.item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Repo repo) {
        ImageLoader.load(
                repo.getOwner().getAvatar_url(), (ImageView) holder.getView(R.id.user_icon));
    }
}
