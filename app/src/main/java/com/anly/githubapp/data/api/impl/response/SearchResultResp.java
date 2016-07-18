package com.anly.githubapp.data.api.impl.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.anly.githubapp.data.model.Repo;

import java.util.ArrayList;

/**
 * Created by mingjun on 16/7/18.
 */
public class SearchResultResp implements Parcelable {

    private long total_count;
    private boolean incomplete_results;
    private ArrayList<Repo> items;

    public long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(long total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public ArrayList<Repo> getItems() {
        return items;
    }

    public void setItems(ArrayList<Repo> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.total_count);
        dest.writeByte(this.incomplete_results ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.items);
    }

    public SearchResultResp() {
    }

    protected SearchResultResp(Parcel in) {
        this.total_count = in.readLong();
        this.incomplete_results = in.readByte() != 0;
        this.items = in.createTypedArrayList(Repo.CREATOR);
    }

    public static final Parcelable.Creator<SearchResultResp> CREATOR = new Parcelable.Creator<SearchResultResp>() {
        @Override
        public SearchResultResp createFromParcel(Parcel source) {
            return new SearchResultResp(source);
        }

        @Override
        public SearchResultResp[] newArray(int size) {
            return new SearchResultResp[size];
        }
    };
}
