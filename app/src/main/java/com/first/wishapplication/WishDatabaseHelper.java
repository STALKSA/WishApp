package com.first.wishapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WishDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wishList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WISHES = "wishes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_URL = "imageUrl";
    private static final String COLUMN_STATUS = "status";

    public WishDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_WISHES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_STATUS + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHES);
        onCreate(db);
    }

    public long addWish(WishItem wishItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, wishItem.getTitle());
        values.put(COLUMN_DESCRIPTION, wishItem.getDescription());
        values.put(COLUMN_IMAGE_URL, wishItem.getImageUrl());
        values.put(COLUMN_STATUS, wishItem.isCompleted() ? 1 : 0);
        long id = db.insert(TABLE_WISHES, null, values);
        db.close();
        return id;
    }

    public List<WishItem> getAllWishes() {
        List<WishItem> wishList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WISHES, null);

        if (cursor.moveToFirst()) {
            do {
                WishItem wishItem = new WishItem(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1
                );
                wishList.add(wishItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wishList;
    }

    public void updateWish(WishItem wishItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, wishItem.getTitle());
        values.put(COLUMN_DESCRIPTION, wishItem.getDescription());
        values.put(COLUMN_IMAGE_URL, wishItem.getImageUrl());
        values.put(COLUMN_STATUS, wishItem.isCompleted() ? 1 : 0);
        db.update(TABLE_WISHES, values, COLUMN_TITLE + " = ?", new String[]{wishItem.getTitle()});
        db.close();
    }

    public void deleteWish(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WISHES, COLUMN_TITLE + " = ?", new String[]{title});
        db.close();
    }
}