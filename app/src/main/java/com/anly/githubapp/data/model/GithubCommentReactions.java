package com.anly.githubapp.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GithubCommentReactions extends HashMap<String, Object> {

    private int totalCount;

    private List<GithubReaction> reactions = new ArrayList<>();

    private String url;

    public List<GithubReaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<GithubReaction> reactions) {
        this.reactions = reactions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
