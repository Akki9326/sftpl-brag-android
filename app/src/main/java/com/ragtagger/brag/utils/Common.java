package com.ragtagger.brag.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by alpesh.rathod on 2/2/2018.
 */

public class Common {

    /**
     * Don't let anyone instantiate this class.
     */
    private Common() {
        throw new Error("Do not need instantiate!");
    }

    public static String getNotNullStringValue(String value) {
        return (value == null ? "" : value);
    }

    public static int getNotNullIntegerValue(String value) {
        int newInt;
        String newValue = null;
        try {
            newValue = (value == null) ? "0" : value;
            newInt = Integer.parseInt(newValue);
        } catch (NumberFormatException nfe) {
            AppLogger.e(nfe.getMessage(),nfe.getCause());
            //nfe.printStackTrace();
            newInt = 0;
        }
        return newInt;
    }

    public static boolean isEmpty(String text) {
        return (TextUtils.isEmpty(text) || TextUtils.isEmpty(text.trim()));
    }

    public static void showKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInputFromInputMethod(
                    view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void loadImageWithGlide() {

    }

    public static boolean isNotNullOrEmpty(String val) {
        return (val == null || "".equals(val.trim())) ? false : true;
    }

    public static int calculateNoOfColumns(Context context,int itemSize) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / itemSize);
        return noOfColumns;
    }

    public static Rect locateView(View v)
    {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try
        {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe)
        {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }


}
