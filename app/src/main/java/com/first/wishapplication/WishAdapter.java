package com.first.wishapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.WishViewHolder> {
    private List<WishItem> wishList;
    private Context context;

    public WishAdapter(List<WishItem> wishList, Context context) {
        this.wishList = wishList;
        this.context = context;
    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wish, parent, false);
        return new WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishViewHolder holder, int position) {
        WishItem wishItem = wishList.get(position);
        holder.titleTextView.setText(wishItem.getTitle());
        holder.statusTextView.setText(wishItem.isCompleted() ? "Сделано" : "Активно");
        Glide.with(context).load(wishItem.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("wishItem", (Serializable) wishItem); // Уточнение типа здесь
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    public static class WishViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView, statusTextView;

        public WishViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}