package com.example.kilkbid;

import java.util.ArrayList;
import java.util.List;

public class CommentModel {
    private String username;
    private String text;
    private List<String> reactions;
    private List<String> replies;

    public CommentModel(String username, String text) {
        this.username = username;
        this.text = text;
        this.reactions = new ArrayList<>();
        this.replies = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public int getReactions() {
        return reactions.size();
    }

    public int getReplies() {
        return replies.size();
    }

    public void addReaction(String reaction) {
        reactions.add(reaction);
    }

    public void addReply(String reply) {
        replies.add(reply);
    }
}
