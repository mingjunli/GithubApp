package com.anly.githubapp.data.api;

import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.RepoDetail;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by mingjun on 16/7/18.
 */
public interface RepoApi {

    /**
     * Query repo by the key,language, sort by starred count desc.
     * @param key
     * @param language
     * @return
     */
    Observable<ArrayList<Repo>> searchMostStarredRepo(String key, String language);

    /**
     * Get repo info by the owner & repo name.
     * @param owner
     * @param repo
     * @return
     */
    Observable<Repo> getRepo(String owner, String repo);

    /**
     * Get repo's details, including repo, contributors, readme, forks.
     * @param owner
     * @param name
     * @return
     */
    Observable<RepoDetail> getRepoDetail(String owner, String name);

    /**
     * Get current user's repositories.
     * @return
     */
    Observable<ArrayList<Repo>> getMyRepos();

    /**
     * Get current user's starred repositories.
     * @return
     */
    Observable<ArrayList<Repo>> getMyStarredRepos();

    /**
     * Star a repository
     */
    Observable<Boolean> starRepo(String owner, String repo);
}
