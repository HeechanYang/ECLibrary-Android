package com.ec.library.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class PxDpUtil {
    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }
}
