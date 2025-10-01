package com.example.kilkbid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.BidViewHolder> {

    private final List<String> bids;

    public BidAdapter(List<String> bids) {
        this.bids = bids;
    }

    @NonNull
    @Override
    public BidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new BidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidViewHolder holder, int position) {
        holder.text.setText(bids.get(position));
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    static class BidViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public BidViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(android.R.id.text1);
        }
    }
}
