package com.example.kilkbid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<CommentModel> commentList;

    public CommentAdapter(List<CommentModel> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentModel comment = commentList.get(position);

        holder.username.setText(comment.getUsername());
        holder.commentText.setText(comment.getText());

        holder.reactionView.setText(comment.getReactions() + " reactions");
        holder.replyView.setText(comment.getReplies() + " replies");

        holder.likeButton.setOnClickListener(v -> {
            comment.addReaction("👍");
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.loveButton.setOnClickListener(v -> {
            comment.addReaction("❤️");
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.replyButton.setOnClickListener(v -> {
            comment.addReply("Reply from you!");
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView username, commentText, reactionView, replyView;
        ImageView likeButton, loveButton, replyButton;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            commentText = itemView.findViewById(R.id.commentText);
            reactionView = itemView.findViewById(R.id.reactions);
            replyView = itemView.findViewById(R.id.replies);
            likeButton = itemView.findViewById(R.id.likeButton);
            loveButton = itemView.findViewById(R.id.loveButton);
            replyButton = itemView.findViewById(R.id.replyButton);
        }
    }
}
