package com.anly.githubapp.ui.module.main.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.config.MainMenuConfig;
import com.anly.githubapp.ui.base.adapter.ArrayListAdapter;

import java.util.ArrayList;

/**
 * Created by mingjun on 16/7/26.
 */
public class MainMenuListAdapter extends ArrayListAdapter<MainMenuConfig.MainMenu> {

    public MainMenuListAdapter(Activity context, ArrayList<MainMenuConfig.MainMenu> menus) {
        super(context);
        mList = menus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder holder;

        if (item == null) {
            item = LayoutInflater.from(mContext).inflate(R.layout.item_main_menu, null);
            holder = new ViewHolder();

            holder.icon = (ImageView) item.findViewById(R.id.icon);
            holder.label = (TextView) item.findViewById(R.id.label);

            item.setTag(holder);
        }
        else {
            holder = (ViewHolder) item.getTag();
        }

        MainMenuConfig.MainMenu menu = mList.get(position);
        holder.icon.setImageResource(menu.iconResId);
        holder.label.setText(menu.labelResId);

        return item;
    }

    static class ViewHolder {
        ImageView icon;
        TextView label;
    }
}
