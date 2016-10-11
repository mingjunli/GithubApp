package com.anly.githubapp.data.model;

public class GithubReaction {
    private final GithubReactionType type;
    private final int value;

    public GithubReaction(GithubReactionType type, int value) {
        this.type = type;
        this.value = value;
    }

    public GithubReactionType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getEmijoByUnicode() + " " + value;
    }

    public String getEmijoByUnicode() {
        return new String(Character.toChars(type.getEmoji()));
    }
}
