package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Milestone extends ShaUrl implements Comparable<Milestone>, Parcelable {

    public static final Creator<Milestone> CREATOR = new Creator<Milestone>() {
        public Milestone createFromParcel(Parcel source) {
            return new Milestone(source);
        }

        public Milestone[] newArray(int size) {
            return new Milestone[size];
        }
    };
    public String title;
    public int number;
    public MilestoneState state;
    public String description;
    public User creator;
    @SerializedName("open_issues")
    public int openIssues;
    @SerializedName("closed_issues")
    public int closedIssues;
    @SerializedName("created_at")
    public Date createdAt;
    @SerializedName("updated_at")
    public Date updatedAt;
    @SerializedName("due_on")
    public String dueOn;

    public Milestone() {
    }

    protected Milestone(Parcel in) {
        super(in);
        this.title = in.readString();
        this.number = in.readInt();
        int tmpState = in.readInt();
        this.state = tmpState == -1 ? null : MilestoneState.values()[tmpState];
        this.description = in.readString();
        this.creator = in.readParcelable(User.class.getClassLoader());
        this.openIssues = in.readInt();
        this.closedIssues = in.readInt();
        long tmpCreated_at = in.readLong();
        this.createdAt = tmpCreated_at == -1 ? null : new Date(tmpCreated_at);
        long tmpUpdated_at = in.readLong();
        this.updatedAt = tmpUpdated_at == -1 ? null : new Date(tmpUpdated_at);
        this.dueOn = in.readString();
    }

    @Override
    public int compareTo(Milestone another) {
        return title.toLowerCase().compareTo(another.title.toLowerCase());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.title);
        dest.writeInt(this.number);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
        dest.writeString(this.description);
        dest.writeParcelable(this.creator, 0);
        dest.writeInt(this.openIssues);
        dest.writeInt(this.closedIssues);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeString(this.dueOn);
    }
}
