package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GithubStatusResponse extends ShaUrl implements Parcelable {
    public static final Creator<GithubStatusResponse> CREATOR = new Creator<GithubStatusResponse>() {
        public GithubStatusResponse createFromParcel(Parcel source) {
            return new GithubStatusResponse(source);
        }

        public GithubStatusResponse[] newArray(int size) {
            return new GithubStatusResponse[size];
        }
    };
    public String state;
    public int total_count;
    public List<GithubStatus> statuses;
    public Repo repository;
    public String commit_url;

    public GithubStatusResponse() {
    }

    protected GithubStatusResponse(Parcel in) {
        super(in);
        this.state = in.readString();
        this.total_count = in.readInt();
        this.statuses = in.createTypedArrayList(GithubStatus.CREATOR);
        this.repository = in.readParcelable(Repo.class.getClassLoader());
        this.commit_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.state);
        dest.writeInt(this.total_count);
        dest.writeTypedList(statuses);
        dest.writeParcelable(this.repository, 0);
        dest.writeString(this.commit_url);
    }
}
