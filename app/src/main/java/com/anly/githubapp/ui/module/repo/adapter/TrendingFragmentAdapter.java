package com.anly.githubapp.ui.module.repo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.anly.githubapp.common.wrapper.AppLog;
import com.anly.githubapp.data.api.TrendingApi;
import com.anly.githubapp.ui.base.adapter.ArrayFragmentPagerAdapter;
import com.anly.githubapp.ui.module.repo.TrendingFragment;

/**
 * Created by mingjun on 16/8/15.
 */
public class TrendingFragmentAdapter extends ArrayFragmentPagerAdapter<Integer> {

    public TrendingFragmentAdapter(FragmentManager fm, Integer[] data) {
        super(fm);
        setList(data);
    }

    @Override
    public Fragment getItem(int position) {
        int lang = mList.get(position);
        return TrendingFragment.newInstance(lang);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int lang = mList.get(position);
        switch (lang) {
            case TrendingApi.LANG_JAVA:
                return "Java";

            case TrendingApi.LANG_OC:
                return "Objective-C";

            case TrendingApi.LANG_SWIFT:
                return "Swift";

            case TrendingApi.LANG_HTML:
                return "HTML";

            case TrendingApi.LANG_PYTHON:
                return "Python";

            case TrendingApi.LANG_BASH:
                return "Shell";

            default:
                AppLog.w("unknown language");
                break;
        }

        return "";
    }
}
