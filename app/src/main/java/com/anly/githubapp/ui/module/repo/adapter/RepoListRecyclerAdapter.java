package com.anly.githubapp.ui.module.repo.adapter;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.model.Repo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mingjun on 16/7/18.
 */
public class RepoListRecyclerAdapter extends BaseQuickAdapter<Repo> {

    public RepoListRecyclerAdapter(List<Repo> data) {
        super(R.layout.item_repo, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Repo repo) {
        holder.setText(R.id.name, StringUtil.replaceAllBlank(repo.getName()));
        holder.setText(R.id.desc, StringUtil.trimNewLine(repo.getDescription()));
    }
}
