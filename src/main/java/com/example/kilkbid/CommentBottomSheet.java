package com.example.kilkbid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentBottomSheet extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private EditText commentInput;
    private Button sendButton;
    private CommentAdapter adapter;
    private List<CommentModel> comments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_comments, container, false);

        recyclerView = view.findViewById(R.id.commentRecycler);
        commentInput = view.findViewById(R.id.commentInput);
        sendButton = view.findViewById(R.id.sendButton);

        adapter = new CommentAdapter(comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String text = commentInput.getText().toString().trim();
            if (!text.isEmpty()) {
                comments.add(new CommentModel("You", text));
                adapter.notifyItemInserted(comments.size() - 1);
                recyclerView.scrollToPosition(comments.size() - 1);
                commentInput.setText("");
            }
        });

        return view;
    }
}
