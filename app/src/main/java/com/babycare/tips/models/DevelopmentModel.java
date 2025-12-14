package com.babycare.tips.models;

import android.graphics.drawable.Drawable;

public class DevelopmentModel {
    String title;
    String content;
    int color;
    Drawable icon;

    public DevelopmentModel(String title, String content, int color, Drawable icon) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.icon = icon;
    }

    public DevelopmentModel() {
    }

    public int getColor() {
        return color;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
