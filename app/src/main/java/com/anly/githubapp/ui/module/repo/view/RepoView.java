package com.anly.githubapp.ui.module.repo.view;

import com.anly.mvp.lce.LoadView;

/**
 * Created by mingjun on 16/8/15.
 */
public interface RepoView extends LoadView {

    void starSuccess();

    void starFailed();
}
