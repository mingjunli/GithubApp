package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GithubStatus implements Parcelable {
    public static final Creator<GithubStatus> CREATOR =
            new Creator<GithubStatus>() {
                public GithubStatus createFromParcel(Parcel source) {
                    return new GithubStatus(source);
                }

                public GithubStatus[] newArray(int size) {
                    return new GithubStatus[size];
                }
            };
    public int id;
    public String created_at;
    public String updated_at;
    public String state;
    public String target_url;
    public String description;
    public String url;
    public String context;

    public GithubStatus() {
    }

    protected GithubStatus(Parcel in) {
        this.id = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.state = in.readString();
        this.target_url = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.context = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.state);
        dest.writeString(this.target_url);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.context);
    }
}
