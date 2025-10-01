package com.example.kilkbid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categorySlider, productGrid;
    private BottomNavigationView bottomNav;
    private ImageView notificationIcon;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Find views
        categorySlider = findViewById(R.id.categorySlider);
        bottomNav = findViewById(R.id.bottomNav);
        notificationIcon = findViewById(R.id.notificationIcon);
        productGrid = findViewById(R.id.productGrid);

        // 2 items per row in grid
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        productGrid.setLayoutManager(gridLayoutManager);
        productGrid.setAdapter(new ProductAdapter(getDummyProducts())); // adapter

        // Setup categories
        setupCategorySlider();
        setupSearchBar();

        //  Notification button click
        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, notifications.class);
            startActivity(intent);
        });

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        productGrid.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels));

        int spacing = getResources().getDimensionPixelSize(R.dimen.category_item_spacing);
        categorySlider.addItemDecoration(new HorizontalSpacingItemDecoration(spacing));

        // Bottom navigation clicks
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_auctions) {
                startActivity(new Intent(this, AuctionActivity.class));
                return true;
            } else if (id == R.id.nav_my_bids) {
                startActivity(new Intent(this, MyBidsActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, profile.class));
                return true;
            }
            return false;
        });
    }

    private void setupSearchBar() {
        EditText searchBar = findViewById(R.id.searchBar);

        // Dummy product list: 10 each category (Small Business, Startup, Medium, Big, Corporation)
        List<String> allProducts = new ArrayList<>();

        // 🔹 Small Business (10)
        allProducts.add("Handmade Soap - Small Business");
        allProducts.add("Local Coffee Beans - Small Business");
        allProducts.add("Organic Honey - Small Business");
        allProducts.add("Homemade Candles - Small Business");
        allProducts.add("Customized Mugs - Small Business");
        allProducts.add("Artisanal Bread - Small Business");
        allProducts.add("Leather Wallet - Small Business");
        allProducts.add("Wooden Crafts - Small Business");
        allProducts.add("Farm Fresh Eggs - Small Business");
        allProducts.add("Vintage Jewelry - Small Business");

        // 🔹 Startup (10)
        allProducts.add("Custom T-Shirts - Startup");
        allProducts.add("Phone Accessories - Startup");
        allProducts.add("Eco-Friendly Bags - Startup");
        allProducts.add("Mobile App Services - Startup");
        allProducts.add("Gaming Gear - Startup");
        allProducts.add("LED Lights - Startup");
        allProducts.add("Wireless Earbuds - Startup");
        allProducts.add("Portable Speakers - Startup");
        allProducts.add("Fitness Equipment - Startup");
        allProducts.add("Digital Art Prints - Startup");

        // 🔹 Medium Enterprise (10)
        allProducts.add("Furniture - Medium Enterprise");
        allProducts.add("Fashion Brand - Medium Enterprise");
        allProducts.add("Appliances - Medium Enterprise");
        allProducts.add("Stationery Supplies - Medium Enterprise");
        allProducts.add("Bags & Luggage - Medium Enterprise");
        allProducts.add("Office Chairs - Medium Enterprise");
        allProducts.add("Shoes & Footwear - Medium Enterprise");
        allProducts.add("Kitchenware - Medium Enterprise");
        allProducts.add("Mattresses - Medium Enterprise");
        allProducts.add("Sportswear - Medium Enterprise");

        // 🔹 Big Business (10)
        allProducts.add("Laptop - Big Business");
        allProducts.add("Smartphone - Big Business");
        allProducts.add("Smart TV - Big Business");
        allProducts.add("Air Conditioner - Big Business");
        allProducts.add("Refrigerator - Big Business");
        allProducts.add("Gaming Console - Big Business");
        allProducts.add("Tablet - Big Business");
        allProducts.add("Smartwatch - Big Business");
        allProducts.add("Camera - Big Business");
        allProducts.add("Headphones - Big Business");

        // 🔹 Corporation (10)
        allProducts.add("Luxury Car - Corporation");
        allProducts.add("Real Estate - Corporation");
        allProducts.add("Private Jet - Corporation");
        allProducts.add("Yacht - Corporation");
        allProducts.add("Oil & Gas Equipment - Corporation");
        allProducts.add("Data Centers - Corporation");
        allProducts.add("Mining Equipment - Corporation");
        allProducts.add("Pharmaceutical Products - Corporation");
        allProducts.add("Financial Services - Corporation");
        allProducts.add("Luxury Hotels - Corporation");

        // Attach adapter with the full list
        productAdapter = new ProductAdapter(allProducts);
        productGrid.setAdapter(productAdapter);

        // Filter when user types
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (productAdapter != null) {
                    productAdapter.filter(s.toString()); // 🔎 live search
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }


    private void setupCategorySlider() {
        List<String> categories = new ArrayList<>();
        categories.add("Electronics");
        categories.add("Fashion");
        categories.add("Home");
        categories.add("Sports");
        categories.add("Toys");

        categorySlider.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        categorySlider.setAdapter(new CategoryAdapter(categories));
    }

    // ✅ Dummy products for grid
    private List<String> getDummyProducts() {
        List<String> products = new ArrayList<>();
        products.add("iPhone 15");
        products.add("Nike Sneakers");
        products.add("Gaming Laptop");
        products.add("Smartwatch");
        products.add("Camera");
        products.add("Bluetooth Speaker");
        return products;
    }
}
