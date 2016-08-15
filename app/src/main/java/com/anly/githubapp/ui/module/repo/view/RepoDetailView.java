package com.anly.githubapp.ui.module.repo.view;

import com.anly.githubapp.data.model.RepoDetail;

/**
 * Created by mingjun on 16/8/9.
 */
public interface RepoDetailView extends RepoView {

    void showRepoDetail(RepoDetail detail);
}
