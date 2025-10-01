package com.example.kilkbid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MyBidsActivity extends AppCompatActivity {

    private Button bidsTab, transactionsTab;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNav;

    private List<Object> bidsList = new ArrayList<>();
    private List<Object> transactionsList = new ArrayList<>();
    private boolean showingBids = true;
    private GenericAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mybids);

        bidsTab = findViewById(R.id.bidsTab);
        transactionsTab = findViewById(R.id.transactionsTab);
        recyclerView = findViewById(R.id.recyclerViewContent);
        bottomNav = findViewById(R.id.bottomNav);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadDummyData();

        adapter = new GenericAdapter(bidsList);
        recyclerView.setAdapter(adapter);
        highlightTab(true);

        // Tab click listeners
        bidsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showingBids = true;
                adapter.updateList(bidsList);
                highlightTab(true);
            }
        });

        transactionsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showingBids = false;
                adapter.updateList(transactionsList);
                highlightTab(false);
            }
        });

        // Bottom navigation listener (fixed for Java)
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_auctions) {
                startActivity(new Intent(this, AuctionActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, profile.class));
                return true;
            }
            return false;
        });
    }

    private void highlightTab(boolean isBids) {
        if (isBids) {
            bidsTab.setBackgroundColor(Color.parseColor("#6200EE"));
            bidsTab.setTextColor(Color.WHITE);
            transactionsTab.setBackgroundColor(Color.LTGRAY);
            transactionsTab.setTextColor(Color.BLACK);
        } else {
            transactionsTab.setBackgroundColor(Color.parseColor("#6200EE"));
            transactionsTab.setTextColor(Color.WHITE);
            bidsTab.setBackgroundColor(Color.LTGRAY);
            bidsTab.setTextColor(Color.BLACK);
        }
    }

    private void loadDummyData() {
        bidsList.add(new BidItem("Laptop", "$150", "01/10/2025"));
        bidsList.add(new BidItem("Phone", "$90", "28/09/2025"));

        transactionsList.add(new TransactionItem("Payment Sent", "$150", "01/10/2025"));
        transactionsList.add(new TransactionItem("Payment Received", "$90", "28/09/2025"));
    }

    // Data classes
    public static class BidItem {
        String title, bid, date;
        public BidItem(String title, String bid, String date) {
            this.title = title;
            this.bid = bid;
            this.date = date;
        }
    }

    public static class TransactionItem {
        String title, amount, date;
        public TransactionItem(String title, String amount, String date) {
            this.title = title;
            this.amount = amount;
            this.date = date;
        }
    }

    // Generic RecyclerView Adapter
    public static class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {

        private List<Object> items;

        public GenericAdapter(List<Object> items) {
            this.items = items;
        }

        public void updateList(List<Object> newItems) {
            items = newItems;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_bid_history, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Object item = items.get(position);
            if (item instanceof BidItem) {
                BidItem bid = (BidItem) item;
                holder.title.setText(bid.title);
                holder.details.setText("Bid: " + bid.bid + " | Date: " + bid.date);
            } else if (item instanceof TransactionItem) {
                TransactionItem trans = (TransactionItem) item;
                holder.title.setText(trans.title);
                holder.details.setText("Amount: " + trans.amount + " | Date: " + trans.date);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, details;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.itemTitle);
                details = itemView.findViewById(R.id.itemDetails);
            }
        }
    }
}
