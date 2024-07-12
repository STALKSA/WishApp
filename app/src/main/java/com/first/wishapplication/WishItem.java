package com.first.wishapplication;
public class WishItem {
    private String title;
    private String description;
    private String imageUrl;
    private boolean isCompleted;

    public WishItem(String title, String description, String imageUrl, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isCompleted = isCompleted;
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}