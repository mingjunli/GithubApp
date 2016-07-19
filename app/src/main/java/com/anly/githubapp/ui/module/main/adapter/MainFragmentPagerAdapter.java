package com.anly.githubapp.ui.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.anly.githubapp.ui.base.adapter.ArrayFragmentPagerAdapter;

/**
 * Created by mingjun on 16/7/19.
 */
public class MainFragmentPagerAdapter extends ArrayFragmentPagerAdapter<Fragment> {

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
}
