package com.anly.githubapp.common.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

/**
 * Created by mingjun on 16/8/31.
 */
public class IconicUtil {

    public static Drawable getSmallIcon (Context context, IIcon icon) {
        return new IconicsDrawable(context)
                .icon(icon)
                .sizeDp(24);
    }
}
