package com.anly.githubapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.anly.githubapp.data.net.response.Content;

import java.util.ArrayList;

/**
 * Created by mingjun on 16/7/29.
 */
public class RepoDetail implements Parcelable {

    private Repo baseRepo;
    private Content readme;
    private ArrayList<Repo> forks;
    private ArrayList<User> contributors;

    public Repo getBaseRepo() {
        return baseRepo;
    }

    public void setBaseRepo(Repo baseRepo) {
        this.baseRepo = baseRepo;
    }

    public Content getReadme() {
        return readme;
    }

    public void setReadme(Content readme) {
        this.readme = readme;
    }

    public ArrayList<Repo> getForks() {
        return forks;
    }

    public void setForks(ArrayList<Repo> forks) {
        this.forks = forks;
    }

    public ArrayList<User> getContributors() {
        return contributors;
    }

    public void setContributors(ArrayList<User> contributors) {
        this.contributors = contributors;
    }

    public RepoDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.baseRepo, flags);
        dest.writeParcelable(this.readme, flags);
        dest.writeTypedList(this.forks);
        dest.writeTypedList(this.contributors);
    }

    protected RepoDetail(Parcel in) {
        this.baseRepo = in.readParcelable(Repo.class.getClassLoader());
        this.readme = in.readParcelable(Content.class.getClassLoader());
        this.forks = in.createTypedArrayList(Repo.CREATOR);
        this.contributors = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<RepoDetail> CREATOR = new Creator<RepoDetail>() {
        @Override
        public RepoDetail createFromParcel(Parcel source) {
            return new RepoDetail(source);
        }

        @Override
        public RepoDetail[] newArray(int size) {
            return new RepoDetail[size];
        }
    };
}
