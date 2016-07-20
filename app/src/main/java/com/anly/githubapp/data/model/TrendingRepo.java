package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mingjun on 16/7/20.
 */
public class TrendingRepo implements Parcelable {

    /**
     * title :  cloudfoundry / bosh
     * description :  Cloud Foundry BOSH is an open source tool chain for release engineering, deployment and lifecycle management of large scale distributed services.
     * readme : http://www.github.com/cloudfoundry/bosh
     */

    private String title;
    private String description;
    private String readme;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.readme);
    }

    public TrendingRepo() {
    }

    protected TrendingRepo(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.readme = in.readString();
    }

    public static final Parcelable.Creator<TrendingRepo> CREATOR = new Parcelable.Creator<TrendingRepo>() {
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
