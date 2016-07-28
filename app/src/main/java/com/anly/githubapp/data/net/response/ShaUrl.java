package com.anly.githubapp.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bernat on 20/07/2014.
 */
public class ShaUrl implements Parcelable {

  private static final int MAX_SHA_LENGHT = 8;
  public String sha;
  public String url;
  public String html_url;

  public ShaUrl() {
  }

  protected ShaUrl(Parcel in) {
    this.sha = in.readString();
    this.url = in.readString();
    this.html_url = in.readString();
  }

  public static String shortShaStatic(String sha) {
    int start = 0;
    int end = Math.min(MAX_SHA_LENGHT, sha.length());

    return sha.substring(start, end);
  }

  public String shortSha() {
    int start = 0;
    int end = Math.min(MAX_SHA_LENGHT, sha.length());

    return sha.substring(start, end);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ShaUrl> CREATOR = new Creator<ShaUrl>() {
    @Override
    public ShaUrl createFromParcel(Parcel source) {
      return new ShaUrl(source);
    }

    @Override
    public ShaUrl[] newArray(int size) {
      return new ShaUrl[size];
    }
  };

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.sha);
    dest.writeString(this.url);
    dest.writeString(this.html_url);
  }
}
