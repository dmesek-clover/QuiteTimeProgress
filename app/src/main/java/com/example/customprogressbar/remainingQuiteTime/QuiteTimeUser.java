package com.example.customprogressbar.remainingQuiteTime;

import android.graphics.drawable.Drawable;

public class QuiteTimeUser {
    private final String name;
    private final Drawable icon;

    public QuiteTimeUser(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
