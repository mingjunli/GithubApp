package com.anly.githubapp.common.wrapper;

import android.content.Context;
import android.widget.ImageView;

import com.anly.githubapp.R;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by mingjun on 16/7/17.
 */
public class ImageLoader {

    /**
     * Load image from source and set it into the imageView.
     * @param context context.
     * @param source could be Uri/String/File/ResourceId.
     * @param view the imageView.
     */
    public static void load(Context context, Object source, ImageView view) {
        Glide.with(context)
                .load(source)
                .centerCrop()
                .into(view);
    }

    public static void load(Object source, ImageView view) {
        Glide.with(view.getContext())
                .load(source)
                .centerCrop()
                .into(view);
    }

    public static void loadWithCircle(Context context, Object source, ImageView view) {
        Glide.with(context)
                .load(source)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.ic_github)
                .into(view);
    }

    public static void loadWithCircle(Object source, ImageView view) {
        Glide.with(view.getContext())
                .load(source)
                .bitmapTransform(new CropCircleTransformation(view.getContext()))
                .into(view);
    }
}
