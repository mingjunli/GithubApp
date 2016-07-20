package com.anly.githubapp.ui.module.main.adapter;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.model.TrendingRepo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mingjun on 16/7/18.
 */
public class TrendingRepoRecyclerAdapter extends BaseQuickAdapter<TrendingRepo> {

    public TrendingRepoRecyclerAdapter(List<TrendingRepo> data) {
        super(R.layout.item_trending_repo, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TrendingRepo repo) {
        holder.setText(R.id.name, StringUtil.replaceAllBlank(repo.getTitle()));
        holder.setText(R.id.desc, StringUtil.trimNewLine(repo.getDescription().trim()));
    }
}
