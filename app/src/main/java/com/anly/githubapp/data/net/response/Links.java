package com.anly.githubapp.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bernat on 20/07/2014.
 */
public class Links implements Parcelable {
  public static final Creator<Links> CREATOR = new Creator<Links>() {
    public Links createFromParcel(Parcel source) {
      return new Links(source);
    }

    public Links[] newArray(int size) {
      return new Links[size];
    }
  };
  public String html;
  public String self;
  public String git;

  public Links() {
  }

  protected Links(Parcel in) {
    this.html = in.readString();
    this.self = in.readString();
    this.git = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.html);
    dest.writeString(this.self);
    dest.writeString(this.git);
  }
}
