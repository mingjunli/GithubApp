package com.anly.githubapp.data.api;

import android.support.annotation.IntDef;

import com.anly.githubapp.data.model.TrendingRepo;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by mingjun on 16/7/20.
 */
public interface TrendingApi {

    public int LANG_JAVA = 1;
    public int LANG_OC = 2;
    public int LANG_SWIFT = 3;
    public int LANG_HTML = 4;
    public int LANG_PYTHON = 5;
    public int LANG_BASH = 6;

    @IntDef({
            LANG_JAVA,
            LANG_OC,
            LANG_SWIFT,
            LANG_HTML,
            LANG_PYTHON,
            LANG_BASH
    })
    @interface LanguageType{

    }

    /**
     * Get trending repo base on type.
     * @param language
     * @return
     */
    Observable<ArrayList<TrendingRepo>> getTrendingRepo(@LanguageType int language);
}
