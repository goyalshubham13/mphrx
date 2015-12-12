package com.mphrx.android.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;

import com.mphrx.android.constants.Constants;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * severals utility functions
 */
public class Utils {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context _context) {
        context = _context;
    }

    public static void initializeScreenDimensions() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Constants.deviceWidth = displayMetrics.widthPixels;
        Constants.deviceHeight = displayMetrics.heightPixels;
        Constants.deviceDensityFactor = displayMetrics.density;
    }
    public static int convertDpToPixel(float deviceDensityFactor, float dp) {
        float px = dp * deviceDensityFactor + 0.5f;
        return (int) px;
    }
    public static String getAppVersion() {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String versionName = info.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
        }
        return "";
    }
}
