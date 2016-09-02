package com.anly.githubapp.data.api;

import android.support.annotation.IntDef;

import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.data.model.RepoDetail;
import com.anly.githubapp.data.net.response.Content;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by mingjun on 16/7/18.
 */
public interface RepoApi {

    public int TYPE_ANDROID = 1;
    public int TYPE_IOS = 2;
    public int TYPE_WEB = 3;
    public int TYPE_PYTHON = 4;
    public int TYPE_PHP = 5;

    @IntDef({
            TYPE_ANDROID,
            TYPE_IOS,
            TYPE_WEB,
            TYPE_PYTHON,
            TYPE_PHP
    })
    @interface MostStarsType{
    }

    /**
     * Get the top 30 stars repositories.
     * @param type
     * @return
     */
    Observable<ArrayList<Repo>> getTop30StarsRepo(@MostStarsType int type);

    /**
     * Search repository by key & language.
     * @param key
     * @param language
     * @return
     */
    Observable<ArrayList<Repo>> searchRepo(String key, String language);

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

    /**
     * Star a repository
     */
    Observable<Boolean> unstarRepo(String owner, String repo);

    /**
     * Check if the repository is starred or not.
     * @param owner
     * @param repo
     * @return
     */
    Observable<Boolean> isStarred(String owner, String repo);

    /**
     * Get repo's readme content.
     * @param owner
     * @param repo
     * @return
     */
    Observable<Content> getRepoReadme(String owner, String repo);

    /**
     * Get repo's content by path. set path to null to get first level contents.
     * @param owner
     * @param repo
     * @return
     */
    Observable<ArrayList<Content>> getRepoContents(String owner, String repo, String path);

    /**
     * Get file content's details, which contain a "content".
     * @param owner
     * @param repo
     * @param path
     * @return
     */
    Observable<Content> getContentDetail(String owner, String repo, String path);
}
