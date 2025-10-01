package com.example.kilkbid;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public HorizontalSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        outRect.left = position == 0 ? spacing : spacing / 2;
        outRect.right = position == itemCount - 1 ? spacing : spacing / 2;
    }
}
