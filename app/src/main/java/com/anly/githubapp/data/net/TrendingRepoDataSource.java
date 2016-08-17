package com.anly.githubapp.data.net;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.data.model.TrendingRepo;
import com.anly.githubapp.data.net.response.TrendingResultResp;
import com.anly.githubapp.data.net.service.TrendingService;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

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
    public Observable<ArrayList<TrendingRepo>> getTrendingRepo(@LanguageType final int language) {
        return mTrendingService.getTrendingRepos()
                .map(new Func1<TrendingResultResp, ArrayList<TrendingRepo>>() {
                    @Override
                    public ArrayList<TrendingRepo> call(TrendingResultResp resp) {

                        switch (language) {
                            case LANG_JAVA:
                                return resp.getJava();

                            case LANG_OC:
                                return resp.getOc();

                            case LANG_SWIFT:
                                return resp.getSwift();

                            case LANG_HTML:
                                return resp.getHtml();

                            case LANG_PYTHON:
                                return resp.getPython();

                            case LANG_BASH:
                                return  resp.getBash();

                            default:
                                AppLog.w("unknown language");
                                break;
                        }

                        return null;
                    }
                });
    }
}
