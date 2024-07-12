package com.first.wishapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WishAdapter wishAdapter;
    private List<WishItem> wishList;
    private WishDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new WishDatabaseHelper(this);
        wishList = dbHelper.getAllWishes();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishAdapter = new WishAdapter(wishList, this);
        recyclerView.setAdapter(wishAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Логика добавления нового элемента
            WishItem newWish = new WishItem("Новая вещь", "Описание", "URL_изображения", false);
            long id = dbHelper.addWish(newWish);
            if (id != -1) {
                wishList.add(newWish);
                wishAdapter.notifyItemInserted(wishList.size() - 1);
            }
        });
    }
}