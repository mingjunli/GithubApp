package com.anly.githubapp.data;

import com.anly.githubapp.common.constant.Constants;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.api.impl.response.SearchResultResp;
import com.anly.githubapp.data.api.impl.service.RepoService;
import com.anly.githubapp.data.model.Repo;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mingjun on 16/7/15.
 */
public class RepoDataSource implements RepoApi {

    private static final String SORT_BY_STARS = "stars";
    private static final String ORDER_BY_DESC = "desc";

    RepoService mRepoService;

    @Inject
    public RepoDataSource(RepoService service) {
        this.mRepoService = service;
    }

    @Override
    public Observable<ArrayList<Repo>> searchMostStarredRepo(String key, String language) {

        // base on https://developer.github.com/v3/search/#search-repositories
        // compose the key
        String q = new StringBuilder().append(key).append("+").append("language:").append(language).toString();
        AppLog.d("searchMostStarredRepo, q:" + q);

        // we get the most starred 30 repos.
        return mRepoService.searchRepo(q, SORT_BY_STARS, ORDER_BY_DESC, 1, Constants.PAGE_SIZE).map(new Func1<SearchResultResp, ArrayList<Repo>>() {
            @Override
            public ArrayList<Repo> call(SearchResultResp searchResultResp) {
                return searchResultResp.getItems();
            }
        });
    }
}
