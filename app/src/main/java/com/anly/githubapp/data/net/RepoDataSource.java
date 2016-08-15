package com.anly.githubapp.data.net;

import android.text.TextUtils;

import com.anly.githubapp.common.constant.Constants;
import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.common.util.StringUtil;
import com.anly.githubapp.data.api.RepoApi;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.data.model.User;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.data.net.response.SearchResultResp;
import com.anly.githubapp.data.net.service.RepoService;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func5;

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
    public Observable<ArrayList<Repo>> getTop30StarsRepo(@MostStarsType int type) {

        // base on https://developer.github.com/v3/search/#search-repositories
        // build to query params.
        StringBuilder queryParams = new StringBuilder();

        String key = "";
        String[] lang = null;
        switch (type) {
            case TYPE_ANDROID:
                key = "android";
                lang = new String[]{"java"};
                break;
            case TYPE_IOS:
                key = "ios";
                lang = new String[]{"Swift", "Objective-C"};
                break;
            case TYPE_PYTHON:
                key = "python";
                lang = new String[]{"python"};
                break;
            case TYPE_WEB:
                key = "web";
                lang = new String[]{"HTML", "CSS", "JavaScript"};
                break;
            case TYPE_PHP:
                key = "php";
                lang = new String[]{"PHP"};
                break;
        }

        queryParams.append(key);
        if (lang != null && lang.length > 0) {
            for (String language : lang) {
                queryParams.append("+language:");
                queryParams.append(language);
            }
        }

        AppLog.d("getTop30StarsRepo, q:" + queryParams.toString());

        // we get the most starred 30 repos.
        return mRepoService.searchRepo(queryParams.toString(), SORT_BY_STARS, ORDER_BY_DESC, 1, Constants.PAGE_SIZE).map(new Func1<SearchResultResp, ArrayList<Repo>>() {
            @Override
            public ArrayList<Repo> call(SearchResultResp searchResultResp) {
                return searchResultResp.getItems();
            }
        });
    }

    @Override
    public Observable<ArrayList<Repo>> searchRepo(String key, String language) {
        // base on https://developer.github.com/v3/search/#search-repositories
        // build to query params.
        StringBuilder queryParams = new StringBuilder().append(key);

        if (!TextUtils.isEmpty(language)) {
            queryParams.append("+language:");
            queryParams.append(language);
        }
        AppLog.d("searchRepo, q:" + queryParams.toString());

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
                isStarred(owner, name),
                new Func5<Repo, ArrayList<User>, ArrayList<Repo>, Content, Boolean, RepoDetail>() {
                    @Override
                    public RepoDetail call(Repo repo, ArrayList<User> users, ArrayList<Repo> forks, Content readme, Boolean isStarred) {
                        RepoDetail detail = new RepoDetail();

                        repo.setStarred(isStarred);
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

    @Override
    public Observable<Boolean> starRepo(String owner, final String repo) {
        return mRepoService.starRepo(owner, repo)
                .map(new Func1<Response, Boolean>() {
                    @Override
                    public Boolean call(Response response) {
                        AppLog.d("response:" + response);
                        return response != null && response.code() == 204;
                    }
                });
    }

    @Override
    public Observable<Boolean> unstarRepo(String owner, String repo) {
        return mRepoService.unstarRepo(owner, repo)
                .map(new Func1<Response, Boolean>() {
                    @Override
                    public Boolean call(Response response) {
                        AppLog.d("response:" + response);
                        return response != null && response.code() == 204;
                    }
                });
    }

    @Override
    public Observable<Boolean> isStarred(String owner, final String repo) {
        return mRepoService.checkIfRepoIsStarred(owner, repo)
                .map(new Func1<Response, Boolean>() {
                    @Override
                    public Boolean call(Response response) {
                        /**
                         * Response if this repository is starred by you
                         *  Status: 204 No Content
                         * Response if this repository is not starred by you
                         *  Status: 404 Not Found
                         */
                        return response != null && response.code() == 204;
                    }
                });
    }
}
