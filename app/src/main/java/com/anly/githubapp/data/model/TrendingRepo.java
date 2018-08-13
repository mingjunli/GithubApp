package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mingjun on 16/7/20.
 */
public class TrendingRepo implements Serializable {

    /**
     "avatar": "https://avatars0.githubusercontent.com/u/15684156?s=40&v=4",
     "desc": "👨‍🎓 Java related : basic, concurrent, algorithm",
     "link": "https://github.com/crossoverJie/Java-Interview",
     "owner": "crossoverJie",
     "repo": "Java-Interview",
     "stars": "9,531"
     */

    @SerializedName("repo")
    public String title;

    @SerializedName("desc")
    public String description;

    public String owner;
    public String stars;

    public String readme;
}
