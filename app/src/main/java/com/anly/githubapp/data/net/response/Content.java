package com.anly.githubapp.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Content extends ShaUrl implements Comparable<Content>, Parcelable {
  public static final Creator<Content> CREATOR = new Creator<Content>() {
    public Content createFromParcel(Parcel source) {
      return new Content(source);
    }

    public Content[] newArray(int size) {
      return new Content[size];
    }
  };
  public ContentType type;
  public int size;
  public String name;
  public String content;
  public String path;
  public String git_url;
  public Links _links;
  public String encoding;
  public List<Content> children;
  public Content parent;

  public Content() {
  }

  protected Content(Parcel in) {
    super(in);
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : ContentType.values()[tmpType];
    this.size = in.readInt();
    this.name = in.readString();
    this.content = in.readString();
    this.path = in.readString();
    this.git_url = in.readString();
    this._links = in.readParcelable(Links.class.getClassLoader());
    this.encoding = in.readString();
    this.children = in.createTypedArrayList(Content.CREATOR);
    this.parent = in.readParcelable(Content.class.getClassLoader());
  }

  public boolean isDir() {
    return ContentType.dir.equals(type);
  }

  public boolean isFile() {
    return ContentType.file.equals(type);
  }

  public boolean isSubmodule() {
    return ContentType.symlink.equals(type);
  }

  @Override
  public int compareTo(Content another) {
    if (isDir()) {
      return another.isDir() ? -name.compareTo(another.name) : 1;
    } else if (another.isDir()) {
      return -1;
    }

    return -name.compareTo(another.name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    dest.writeInt(this.size);
    dest.writeString(this.name);
    dest.writeString(this.content);
    dest.writeString(this.path);
    dest.writeString(this.git_url);
    dest.writeParcelable(this._links, 0);
    dest.writeString(this.encoding);
    dest.writeTypedList(children);
    dest.writeParcelable(this.parent, 0);
  }
}
