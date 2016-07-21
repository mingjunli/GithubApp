package com.anly.githubapp.common.config;

import android.content.Context;
import android.content.res.Resources;

import com.anly.githubapp.R;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;

/**
 * Created by mingjun on 16/7/19.
 */
public class MainMenuConfig {

    public static ArrayList<NavigationTabBar.Model> getNavModels() {
        return mNavModels;
    }

    private static ArrayList<NavigationTabBar.Model> mNavModels;

    public static void init(Context context) {
        mNavModels = new ArrayList<>();

        Resources res = context.getResources();
        mNavModels.add(new NavigationTabBar.Model.Builder(
                            res.getDrawable(R.drawable.ic_trending_up), res.getColor(R.color.menu_1))
                            .title(res.getString(R.string.menu_trending))
                            .build());
//
//        mNavModels.add(new NavigationTabBar.Model.Builder(
//                            res.getDrawable(R.drawable.ic_most_star), res.getColor(R.color.menu_2))
//                            .title(res.getString(R.string.menu_most_star))
//                            .build());

        mNavModels.add(new NavigationTabBar.Model.Builder(
                            res.getDrawable(R.drawable.ic_search), res.getColor(R.color.menu_3))
                            .title(res.getString(R.string.menu_search))
                            .build());
    }
}
