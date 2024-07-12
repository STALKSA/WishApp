package com.first.wishapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private WishItem wishItem;
    private TextView detailTitleTextView, detailDescriptionTextView;
    private ImageView detailImageView;
    private Button deleteButton, toggleStatusButton;
    private WishDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new WishDatabaseHelper(this);

        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailDescriptionTextView = findViewById(R.id.detailDescriptionTextView);
        detailImageView = findViewById(R.id.detailImageView);
        deleteButton = findViewById(R.id.deleteButton);
        toggleStatusButton = findViewById(R.id.toggleStatusButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("wishItem")) {
            // Используем Serializable для получения объекта WishItem
            wishItem = (WishItem) intent.getSerializableExtra("wishItem");

            // Проверяем, что wishItem не null перед использованием его свойств
            if (wishItem != null) {
                detailTitleTextView.setText(wishItem.getTitle());
                detailDescriptionTextView.setText(wishItem.getDescription());
                Glide.with(this).load(wishItem.getImageUrl()).into(detailImageView);
                toggleStatusButton.setText(wishItem.isCompleted() ? "Сделано" : "Активно");
            }
        }

        deleteButton.setOnClickListener(v -> {
            if (wishItem != null) {
                dbHelper.deleteWish(wishItem.getTitle());
            }
            finish();
        });

        toggleStatusButton.setOnClickListener(v -> {
            if (wishItem != null) {
                wishItem.setCompleted(!wishItem.isCompleted());
                dbHelper.updateWish(wishItem);
                toggleStatusButton.setText(wishItem.isCompleted() ? "Сделано" : "Активно");
            }
        });
    }
}