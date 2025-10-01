package com.example.kilkbid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final List<String> productList;       // Current list (filtered)
    private final List<String> productListFull;   // Full copy (all products)

    public ProductAdapter(List<String> productList) {
        this.productList = new ArrayList<>(productList);
        this.productListFull = new ArrayList<>(productList); // Copy for filtering
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        String product = productList.get(position);
        holder.productName.setText(product);
        holder.productImage.setImageResource(R.drawable.ic_placeholder); // placeholder
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
        }
    }

    // ✅ Search filter logic
    public void filter(String text) {
        productList.clear();
        if (text.isEmpty()) {
            productList.addAll(productListFull);
        } else {
            text = text.toLowerCase();
            for (String item : productListFull) {
                if (item.toLowerCase().contains(text)) {
                    productList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
