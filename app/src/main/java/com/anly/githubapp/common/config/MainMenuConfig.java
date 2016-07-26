package com.anly.githubapp.common.config;

import com.anly.githubapp.R;
import com.anly.githubapp.ui.module.main.MostStarFragment;
import com.anly.githubapp.ui.module.main.SearchFragment;
import com.anly.githubapp.ui.module.main.TrendingFragment;

import java.util.ArrayList;

/**
 * Created by mingjun on 16/7/19.
 */
public class MainMenuConfig {

    public static ArrayList<MainMenu> MENUS = new ArrayList<>();

    static {
        MENUS.add(new MainMenu(
                R.drawable.ic_trending_up,
                R.string.menu_trending,
                TrendingFragment.class.getName()));

        MENUS.add(new MainMenu(
                R.drawable.ic_most_star,
                R.string.menu_most_star,
                MostStarFragment.class.getName()));

        MENUS.add(new MainMenu(
                R.drawable.ic_search,
                R.string.menu_search,
                SearchFragment.class.getName()));
    }

    public static class MainMenu {
        public int iconResId;
        public int labelResId;
        public String fragmentClass;

        public MainMenu(int iconResId, int labelResId, String fragmentClass) {
            this.iconResId = iconResId;
            this.labelResId = labelResId;
            this.fragmentClass = fragmentClass;
        }
    }
}
