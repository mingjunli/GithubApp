package com.anly.githubapp.ui.module.repo.view;

import com.anly.mvp.lce.LoadView;

/**
 * Created by mingjun on 16/8/11.
 */
public interface SearchView<M> extends LoadView {

    void showSearchResult(M result);

    void error(Throwable e);
}
