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

    public static final long ID_TREND = 1001;
    public static final long ID_STARS = 1002;
    public static final long ID_SEARCH = 1003;

    static {
        MENUS.add(new MainMenu(
                ID_TREND,
                R.drawable.ic_trending_up,
                R.string.menu_trending,
                TrendingFragment.class.getName()));

        MENUS.add(new MainMenu(
                ID_STARS,
                R.drawable.ic_most_star,
                R.string.menu_most_star,
                MostStarFragment.class.getName()));

        MENUS.add(new MainMenu(
                ID_SEARCH,
                R.drawable.ic_search,
                R.string.menu_search,
                SearchFragment.class.getName()));
    }

    public static class MainMenu {
        public long id;
        public int iconResId;
        public int labelResId;
        public String fragmentClass;

        public MainMenu(long id, int iconResId, int labelResId, String fragmentClass) {
            this.id = id;
            this.iconResId = iconResId;
            this.labelResId = labelResId;
            this.fragmentClass = fragmentClass;
        }
    }
}
