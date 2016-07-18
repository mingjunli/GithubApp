package com.anly.githubapp.data.api;

import com.anly.githubapp.data.model.Repo;

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
}
