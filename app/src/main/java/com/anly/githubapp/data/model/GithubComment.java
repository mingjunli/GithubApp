package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GithubComment extends ShaUrl implements Parcelable {

    public static final Creator<GithubComment> CREATOR = new Creator<GithubComment>() {
        @Override
        public GithubComment createFromParcel(Parcel in) {
            return new GithubComment(in);
        }

        @Override
        public GithubComment[] newArray(int size) {
            return new GithubComment[size];
        }
    };
    private static final int MAX_MESSAGE_LENGHT = 146;
    public String id;
    public String body;
    public String body_html;
    public User user;
    public String created_at;
    public String updated_at;
    public GithubCommentReactions reactions;

    public GithubComment() {
    }

    protected GithubComment(Parcel in) {
        super(in);
        this.id = in.readString();
        this.body = in.readString();
        this.body_html = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.created_at = in.readString();
        this.updated_at = in.readString();
    }

    public String shortMessage() {
        if (body != null) {
            if (body.length() > MAX_MESSAGE_LENGHT) {
                return body.substring(0, MAX_MESSAGE_LENGHT).concat("...");
            } else {
                return body;
            }
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.body);
        dest.writeString(this.body_html);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
    }
}
