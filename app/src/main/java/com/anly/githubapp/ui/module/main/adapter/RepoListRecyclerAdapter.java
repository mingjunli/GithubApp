package com.anly.githubapp.ui.module.main.adapter;

import android.widget.ImageView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.ImageLoader;
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
        holder.setText(R.id.name, repo.getName());
        holder.setText(R.id.desc, repo.getDescription());
        holder.setText(R.id.owner, repo.getOwner().getLogin());

        ImageView imageView = holder.getView(R.id.image);
        ImageLoader.load(imageView.getContext(), repo.getOwner().getAvatar_url(), imageView);

        holder.setText(R.id.watch, String.valueOf(repo.getWatchers_count()));
        holder.setText(R.id.star, String.valueOf(repo.getStargazers_count()));
        holder.setText(R.id.fork, String.valueOf(repo.getForks_count()));
    }
}
