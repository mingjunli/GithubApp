package com.anly.githubapp.ui.module.repo.adapter;

import com.anly.githubapp.R;
import com.anly.githubapp.data.model.TrendingRepo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mingjun on 16/7/18.
 */
public class TrendingRepoRecyclerAdapter extends BaseQuickAdapter<TrendingRepo> {

    public TrendingRepoRecyclerAdapter(List<TrendingRepo> data) {
        super(R.layout.item_repo, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TrendingRepo repo) {
        holder.setText(R.id.name, repo.name);
        holder.setText(R.id.desc, repo.desc);
    }
}
