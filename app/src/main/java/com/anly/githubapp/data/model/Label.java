package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Label extends ShaUrl implements Comparable<Label>, Parcelable {

    public static final Creator<Label> CREATOR = new Creator<Label>() {
        public Label createFromParcel(Parcel source) {
            return new Label(source);
        }

        public Label[] newArray(int size) {
            return new Label[size];
        }
    };
    public String name;
    public String color;

    public Label() {
    }

    protected Label(Parcel in) {
        super(in);
        this.name = in.readString();
        this.color = in.readString();
    }

    @Override
    public int compareTo(Label another) {
        return name.toLowerCase().compareTo(another.name.toLowerCase());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.color);
    }
}
