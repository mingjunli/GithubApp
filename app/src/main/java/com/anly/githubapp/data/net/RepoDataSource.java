package com.anly.githubapp.data.net;

import android.text.TextUtils;

import com.anly.githubapp.common.constant.Constants;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.data.net.response.SearchResultResp;
import com.anly.githubapp.data.net.service.RepoService;
import com.anly.githubapp.data.model.Repo;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func4;

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
        // build to query params.
        StringBuilder queryParams = new StringBuilder().append(key);
        if (!TextUtils.isEmpty(language)) {
            queryParams.append("+language:");
            queryParams.append(language);
        }
        AppLog.d("searchMostStarredRepo, q:" + queryParams.toString());

        // we get the most starred 30 repos.
        return mRepoService.searchRepo(queryParams.toString(), SORT_BY_STARS, ORDER_BY_DESC, 1, Constants.PAGE_SIZE).map(new Func1<SearchResultResp, ArrayList<Repo>>() {
            @Override
            public ArrayList<Repo> call(SearchResultResp searchResultResp) {
                return searchResultResp.getItems();
            }
        });
    }

    @Override
    public Observable<Repo> getRepo(String owner, String repo) {
        return mRepoService.get(owner, repo);
    }

    @Override
    public Observable<RepoDetail> getRepoDetail(String owner, String name) {
        return Observable.zip(mRepoService.get(owner, name),
                mRepoService.contributors(owner, name),
                mRepoService.listForks(owner, name, "newest"),
                mRepoService.readme(owner, name),
                new Func4<Repo, ArrayList<User>, ArrayList<Repo>, Content, RepoDetail>() {
                    @Override
                    public RepoDetail call(Repo repo, ArrayList<User> users, ArrayList<Repo> forks, Content readme) {
                        RepoDetail detail = new RepoDetail();
                        detail.setBaseRepo(repo);
                        detail.setForks(forks);

                        // because the readme content is encode with Base64 by github.
                        readme.content = StringUtil.base64Decode(readme.content);
                        detail.setReadme(readme);

                        detail.setContributors(users);
                        return detail;
                    }
                });
    }

    @Override
    public Observable<ArrayList<Repo>> getMyRepos() {
        return mRepoService.getMyRepos();
    }

    @Override
    public Observable<ArrayList<Repo>> getMyStarredRepos() {
        return mRepoService.getMyStarredRepos();
    }
}
