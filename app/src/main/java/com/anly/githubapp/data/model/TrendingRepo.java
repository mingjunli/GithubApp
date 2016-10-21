package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mingjun on 16/7/20.
 */
public class TrendingRepo implements Parcelable {

    /**
     *
     avatar: "https://avatars2.githubusercontent.com/u/7720173?v=3&s=40",
     desc: "Pure Javascript OCR for 62 Languages 📖 🎉 🖥",
     link: "https://github.comnaptha/tesseract.js",
     owner: "naptha",
     repo: "tesseract.js",
     stars: 663
     */

    public String avatar;
    public String desc;
    public String link;
    public String owner;

    @SerializedName("repo")
    public String name;
    public String stars;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.desc);
        dest.writeString(this.link);
        dest.writeString(this.owner);
        dest.writeString(this.name);
        dest.writeString(this.stars);
    }

    public TrendingRepo() {
    }

    protected TrendingRepo(Parcel in) {
        this.avatar = in.readString();
        this.desc = in.readString();
        this.link = in.readString();
        this.owner = in.readString();
        this.name = in.readString();
        this.stars = in.readString();
    }

    public static final Creator<TrendingRepo> CREATOR = new Creator<TrendingRepo>() {
        @Override
        public TrendingRepo createFromParcel(Parcel source) {
            return new TrendingRepo(source);
        }

        @Override
        public TrendingRepo[] newArray(int size) {
            return new TrendingRepo[size];
        }
    };
}
