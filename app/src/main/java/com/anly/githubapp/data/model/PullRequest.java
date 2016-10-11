package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PullRequest extends Issue implements Parcelable {

    public static final Creator<PullRequest> CREATOR = new Creator<PullRequest>() {
        public PullRequest createFromParcel(Parcel source) {
            return new PullRequest(source);
        }

        public PullRequest[] newArray(int size) {
            return new PullRequest[size];
        }
    };
    public Head head;
    public Head base;
    public int additions;
    public int deletions;
    public int commits;
    public int changed_files;
    public boolean merged;
    public Boolean mergeable;
    public String patch_url;
    public String diff_url;
    public GithubStatusResponse statusResponse;

    public PullRequest() {
    }

    protected PullRequest(Parcel in) {
        super(in);
        this.head = in.readParcelable(Head.class.getClassLoader());
        this.statusResponse = in.readParcelable(GithubStatusResponse.class.getClassLoader());
        this.base = in.readParcelable(Head.class.getClassLoader());
        this.additions = in.readInt();
        this.deletions = in.readInt();
        this.commits = in.readInt();
        this.changed_files = in.readInt();
        this.merged = in.readByte() != 0;
        this.mergeable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.patch_url = in.readString();
        this.diff_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.head, 0);
        dest.writeParcelable(this.base, 0);
        dest.writeParcelable(this.statusResponse, 0);
        dest.writeInt(this.additions);
        dest.writeInt(this.deletions);
        dest.writeInt(this.commits);
        dest.writeInt(this.changed_files);
        dest.writeByte(merged ? (byte) 1 : (byte) 0);
        dest.writeValue(this.mergeable);
        dest.writeString(this.patch_url);
        dest.writeString(this.diff_url);
    }
}
