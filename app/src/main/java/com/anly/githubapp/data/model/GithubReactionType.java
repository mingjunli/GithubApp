package com.anly.githubapp.data.model;

public enum GithubReactionType {

    PlusOne("+1", 0x1F44D),
    MinusOne("-1", 0x1F44E),
    Laugh("laugh", 0x1F604),
    Confused("confused", 0x1F615),
    Heart("heart", 0x2764),
    Hooray("hooray", 0x1F389);

    private final String githubKey;
    private final int emoji;

    GithubReactionType(String githubKey, int emoji) {
        this.githubKey = githubKey;
        this.emoji = emoji;
    }

    public String getGithubKey() {
        return githubKey;
    }

    public int getEmoji() {
        return emoji;
    }

}
