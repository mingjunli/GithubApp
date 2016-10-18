package com.anly.githubapp.data.net;

import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.data.net.service.TrendingService;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by mingjun on 16/7/20.
 */
public class TrendingRepoDataSource implements TrendingApi {

    TrendingService mTrendingService;

    @Inject
    public TrendingRepoDataSource(TrendingService service) {
        this.mTrendingService = service;
    }

    @Override
    public Observable<ArrayList<TrendingRepo>> getTrendingRepo(String language, String since) {
        return mTrendingService.getTrendingRepos(language, since);
    }
}
