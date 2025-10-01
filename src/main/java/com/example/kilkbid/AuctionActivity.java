package com.example.kilkbid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuctionActivity extends AppCompatActivity {

    private TextView currentBidText, timerText;
    private Button placeBidButton, commentButton;
    private RecyclerView bidHistoryRecycler;
    private BottomNavigationView bottomNav;

    private List<String> bidList = new ArrayList<>();
    private BidAdapter bidAdapter;

    private int currentBid = 1500;
    private CountDownTimer countDownTimer;
    private long timeLeftMillis = 6300_000; // 1 hr 45 mins

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auction);

        initViews();
        setupRecycler();
        setupBottomNav();
        setupTimer();
        setupButtons();
    }

    private void initViews() {
        currentBidText = findViewById(R.id.currentBid);
        timerText = findViewById(R.id.timer);
        placeBidButton = findViewById(R.id.placeBidButton);
        commentButton = findViewById(R.id.commentButton);
        bidHistoryRecycler = findViewById(R.id.bidHistory);
        bottomNav = findViewById(R.id.bottomNav);

        // ✅ Highlight current tab
        bottomNav.setSelectedItemId(R.id.nav_auctions);
    }

    private void setupRecycler() {
        bidAdapter = new BidAdapter(bidList);
        bidHistoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        bidHistoryRecycler.setAdapter(bidAdapter);
    }

    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_my_bids) {
                startActivity(new Intent(this, MyBidsActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, profile.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }

    private void setupTimer() {
        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerText.setText("Auction ended");
                placeBidButton.setEnabled(false);
                commentButton.setEnabled(false);
            }
        }.start();
    }

    private void updateTimer() {
        int hours = (int) (timeLeftMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),
                "%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(timeFormatted);
    }

    private void setupButtons() {
        placeBidButton.setOnClickListener(v -> {
            currentBid += 100;
            String bidText = "Bid placed: ₱" + currentBid;
            currentBidText.setText("₱" + currentBid);
            bidList.add(0, bidText);
            bidAdapter.notifyItemInserted(0);
            bidHistoryRecycler.scrollToPosition(0);
            Toast.makeText(this, "You placed a bid!", Toast.LENGTH_SHORT).show();
        });

        commentButton.setOnClickListener(v -> openCommentSection());
    }

    private void openCommentSection() {
        CommentBottomSheet bottomSheet = new CommentBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "CommentBottomSheet");
    }
}
