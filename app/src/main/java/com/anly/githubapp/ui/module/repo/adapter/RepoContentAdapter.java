package com.anly.githubapp.ui.module.repo.adapter;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.net.response.Content;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.octicons_typeface_library.Octicons;

import java.util.List;

/**
 * Created by mingjun on 16/7/18.
 */
public class RepoContentAdapter extends BaseQuickAdapter<Content> {

    public RepoContentAdapter(List<Content> data) {
        super(R.layout.item_content, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Content content) {

        IconicsImageView typeIndicator = holder.getView(R.id.type);
        if (content.isDir()) {
            typeIndicator.setIcon(Octicons.Icon.oct_file_directory);
        }
        else if (content.isFile()) {
            typeIndicator.setIcon(Octicons.Icon.oct_file_binary);
        }
        else if (content.isSubmodule()) {
            typeIndicator.setIcon(Octicons.Icon.oct_file_submodule);
        }
        else {
            typeIndicator.setIcon(Octicons.Icon.oct_file_symlink_file);
        }

        holder.setText(R.id.file_name, content.name);
    }
}
