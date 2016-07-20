package com.anly.githubapp.data.net.response;

import com.anly.githubapp.data.model.TrendingRepo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mingjun on 16/7/20.
 */
public class TrendingResultResp {

    @SerializedName("java")
    private ArrayList<TrendingRepo> java;

    @SerializedName("objective-c")
    private ArrayList<TrendingRepo> oc;

    @SerializedName("swift")
    private ArrayList<TrendingRepo> swift;

    @SerializedName("bash")
    private ArrayList<TrendingRepo> bash;

    @SerializedName("python")
    private ArrayList<TrendingRepo> python;

    @SerializedName("html")
    private ArrayList<TrendingRepo> html;

    public ArrayList<TrendingRepo> getJava() {
        return java;
    }

    public void setJava(ArrayList<TrendingRepo> java) {
        this.java = java;
    }

    public ArrayList<TrendingRepo> getOc() {
        return oc;
    }

    public void setOc(ArrayList<TrendingRepo> oc) {
        this.oc = oc;
    }

    public ArrayList<TrendingRepo> getSwift() {
        return swift;
    }

    public void setSwift(ArrayList<TrendingRepo> swift) {
        this.swift = swift;
    }

    public ArrayList<TrendingRepo> getBash() {
        return bash;
    }

    public void setBash(ArrayList<TrendingRepo> bash) {
        this.bash = bash;
    }

    public ArrayList<TrendingRepo> getPython() {
        return python;
    }

    public void setPython(ArrayList<TrendingRepo> python) {
        this.python = python;
    }

    public ArrayList<TrendingRepo> getHtml() {
        return html;
    }

    public void setHtml(ArrayList<TrendingRepo> html) {
        this.html = html;
    }
}
