package com.anly.githubapp.ui.module.repo.view;

import com.anly.githubapp.data.model.RepoDetail;
import com.anly.mvp.lce.LoadView;

/**
 * Created by mingjun on 16/8/9.
 */
public interface RepoDetailView extends LoadView {

    void showRepoDetail(RepoDetail detail);
}
