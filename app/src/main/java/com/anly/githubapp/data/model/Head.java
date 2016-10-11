package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Head extends ShaUrl implements Parcelable {

    public static final Creator<Head> CREATOR = new Creator<Head>() {
        public Head createFromParcel(Parcel source) {
            return new Head(source);
        }

        public Head[] newArray(int size) {
            return new Head[size];
        }
    };
    public String ref;
    public Repo repo;
    public String label;
    public User user;

    public Head() {
    }

    protected Head(Parcel in) {
        super(in);
        this.ref = in.readString();
        this.repo = in.readParcelable(Repo.class.getClassLoader());
        this.label = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.ref);
        dest.writeParcelable(this.repo, 0);
        dest.writeString(this.label);
        dest.writeParcelable(this.user, 0);
    }
}
