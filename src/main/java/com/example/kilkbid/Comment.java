package com.example.kilkbid;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private final String username;
    private final String text;
    private final List<String> reactionList = new ArrayList<>();
    private final List<String> replyList = new ArrayList<>();

    public Comment(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() { return username; }
    public String getText() { return text; }
    public int getReactions() { return reactionList.size(); }
    public int getReplies() { return replyList.size(); }

    public void addReaction(String emoji) { reactionList.add(emoji); }
    public void addReply(String replyText) { replyList.add(replyText); }
}
