package com.example.tetiana.popularmovies;

import android.app.Activity;
import android.util.DisplayMetrics;

class NumberOfColumns {

    int numberOfColumns(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 3) return 2;
        if (nColumns < 5) return 3;
        return nColumns;
    }
}