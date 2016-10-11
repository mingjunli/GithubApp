package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Issue extends GithubComment implements Parcelable {

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        public Issue createFromParcel(Parcel source) {
            return new Issue(source);
        }

        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
    public int number;
    public IssueState state;
    public boolean locked;
    public String title;
    public List<Label> labels;
    public User assignee;
    public Milestone milestone;
    public int comments;
    @SerializedName("pull_request")
    public PullRequest pullRequest;
    @SerializedName("closed_at")
    public String closedAt;

    public Repo repository;

    public Issue() {
    }

    protected Issue(Parcel in) {
        super(in);
        this.number = in.readInt();
        int tmpState = in.readInt();
        this.state = tmpState == -1 ? null : IssueState.values()[tmpState];
        this.locked = in.readByte() != 0;
        this.title = in.readString();
        this.labels = in.createTypedArrayList(Label.CREATOR);
        this.assignee = in.readParcelable(User.class.getClassLoader());
        this.milestone = in.readParcelable(Milestone.class.getClassLoader());
        this.comments = in.readInt();
        this.pullRequest = in.readParcelable(PullRequest.class.getClassLoader());
        this.closedAt = in.readString();
        this.repository = in.readParcelable(Repo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.number);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
        dest.writeByte(locked ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeTypedList(labels);
        dest.writeParcelable(this.assignee, 0);
        dest.writeParcelable(this.milestone, 0);
        dest.writeInt(this.comments);
        dest.writeParcelable(this.pullRequest, 0);
        dest.writeString(this.closedAt);
        dest.writeParcelable(this.repository, 0);
    }
}
