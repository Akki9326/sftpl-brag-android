package com.ragtagger.brag.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ragtagger.brag.R;


/**
 * Created by alpesh.rathod on 2/2/2018.
 */

public class SnackBarUtils {


    public SnackBarUtils() {
        throw new Error("Do not need instantiate!");
    }


    private static Snackbar createSnackBar(Context context, String message, String action, View.OnClickListener
            clickListener, View content, int length) {
        Snackbar snackbar = Snackbar.make(content, message, length);
        if (action != null) {
            snackbar.setAction(action, clickListener);
            if (context != null) {
                snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
        }

        try {
            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
        } catch (Exception e) {
            AppLogger.e(e.getMessage(),e.getCause());
            //e.printStackTrace();
        }

        return snackbar;
    }

    public static Snackbar getSnackbar(Activity activity, final String message) {
        final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content == null)
            return null;
        return createSnackBar(activity, message, null, null, content, Snackbar.LENGTH_SHORT);
    }

    public static Snackbar getSnackbar(Activity activity, final String message, int
            length) {
        final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content == null)
            return null;
        return createSnackBar(activity, message, null, null, content, length);
    }

    public static Snackbar getSnackbar(Activity activity, final String message, String
            action, View
                                               .OnClickListener clickListener) {
        final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content == null)
            return null;
        return createSnackBar(activity, message, action, clickListener, content, Snackbar.LENGTH_SHORT);
    }

    public static Snackbar getSnackbar(Activity activity, final String message, String
            action, View.OnClickListener clickListener, int length) {
        final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content == null)
            return null;
        return createSnackBar(activity, message, action, clickListener, content, length);
    }

    public static Snackbar getSnackbar(Context context,View view, String msg, String action, View.OnClickListener
            clickListener, int length) {
        return createSnackBar(context,msg, action, clickListener, view, length);
    }
}
