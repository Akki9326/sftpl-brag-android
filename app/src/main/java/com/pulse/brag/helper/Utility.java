package com.pulse.brag.helper;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.NetworkOnMainThreadException;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.pulse.brag.BuildConfig;
import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.activities.ChangePasswordActivity;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.net.ConnectException;
import java.security.SecureRandom;
import java.util.StringTokenizer;
import java.util.concurrent.TimeoutException;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class Utility {


    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static Dialog alertDialog;

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static boolean isConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr != null) {
            NetworkInfo[] info = conMgr.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void hideSoftkeyboard(Activity context) {
        if (context.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);

        }

    }

    public static void hideSoftkeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    public static void applyTypeFace(Context context, ViewGroup view) {
        try {
            for (int i = 0; i < view.getChildCount(); i++) {
                if (view.getChildAt(i) instanceof ViewGroup) {
                    applyTypeFace(context, (ViewGroup) view.getChildAt(i));
                } else {
                    if (view.getChildAt(i).getTag() != null) {
                        if (view.getChildAt(i) instanceof TextView) {
                            ((TextView) view.getChildAt(i)).setTypeface(FontProvider.get(context, getFontType(context, view.getChildAt(i))));
                        } else if (view.getChildAt(i) instanceof EditText) {
                            ((EditText) view.getChildAt(i)).setTypeface(FontProvider.get(context, getFontType(context, view.getChildAt(i))));
                        } else if (view.getChildAt(i) instanceof CheckBox) {
                            ((CheckBox) view.getChildAt(i)).setTypeface(FontProvider.get(context, getFontType(context, view.getChildAt(i))));
                        } else if (view.getChildAt(i) instanceof Button) {
                            ((Button) view.getChildAt(i)).setTypeface(FontProvider.get(context, getFontType(context, view.getChildAt(i))));
                        } else if (view.getChildAt(i) instanceof AutoCompleteTextView) {
                            ((AutoCompleteTextView) view.getChildAt(i)).setTypeface(FontProvider.get(context, getFontType(context, view.getChildAt(i))));
                        } else if (view.getChildAt(i) instanceof MultiAutoCompleteTextView) {
                            ((MultiAutoCompleteTextView) view.getChildAt(i)).setTypeface(FontProvider.get(context, getFontType(context, view.getChildAt(i))));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFontType(Context context, View view) {
        try {
            switch (Integer.parseInt(view.getTag().toString())) {
                case Constants.FONT_SANS_ROUNDED:
                    return context.getResources().getString(R.string.font_sans_rounded);
//                case Constants.FONT_SEMIBOLD_ID:
//                    return context.getResources().getString(R.string.font_semibold);
//                case Constants.FONT_LIGHT_ID:
//                    return context.getResources().getString(R.string.font_light);
//                case Constants.FONT_BOOK_ID:
//                    return context.getResources().getString(R.string.font_book);
//                case Constants.FONT_BOOK_ITALIC_ID:
//                    return context.getResources().getString(R.string.font_book_italic);
                default:
                    return context.getResources().getString(R.string.font_sans_rounded);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getString(R.string.font_sans_rounded);
    }

    public static void expandTextView(TextView tv) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount());
        animation.setDuration(200).start();
    }

    public static void collapseTextView(TextView tv, int numLines) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", numLines);
        animation.setDuration(200).start();
    }


    public static void showAlertMessage(final Context mContext, Integer code) {
        String message = "";
        if (code == null)
            code = 1;

        switch (code) {

            case 0:
                message = mContext.getResources().getString(R.string.error_internet);
                break;

            case 1:
                message = mContext.getResources().getString(R.string.error_code_1);
                break;

            case 2:
                message = mContext.getResources().getString(R.string.error_code_2);
                break;

            case 3:
                message = mContext.getResources().getString(R.string.error_code_3);
                break;

            case 4:
                message = mContext.getResources().getString(R.string.error_code_4);
                break;

            case 5:
                message = mContext.getResources().getString(R.string.error_code_5);
                break;

            case 6:
                message = mContext.getResources().getString(R.string.error_code_6);
                break;

            case 7:
                message = mContext.getResources().getString(R.string.error_code_7);
                break;

            case 8:
                message = mContext.getResources().getString(R.string.error_code_8);
                break;

            case 9:
                message = mContext.getResources().getString(R.string.error_code_9);
                break;

            case 10:
                message = mContext.getResources().getString(R.string.error_code_10);
                break;

            case 11:
                message = mContext.getResources().getString(R.string.error_code_11);
                break;

            case 12:
                message = mContext.getResources().getString(R.string.error_code_12);
                break;

            case 13:
                message = mContext.getResources().getString(R.string.error_code_13);
                break;

            case 14:
                message = mContext.getResources().getString(R.string.error_code_14);
                break;

            case 15:
                message = mContext.getResources().getString(R.string.error_code_15);
                break;

            case 16:
                message = mContext.getResources().getString(R.string.error_code_16);
                break;

            case 17:
                message = mContext.getResources().getString(R.string.error_code_17);
                break;

            case 18:
                message = mContext.getResources().getString(R.string.error_code_18);
                break;

            case 19:
                message = mContext.getResources().getString(R.string.error_code_19);
                break;

            case 20:
                message = mContext.getResources().getString(R.string.error_code_20);
                break;

            case 21:
                message = mContext.getResources().getString(R.string.error_code_21);
                break;
            case 22:
                message = mContext.getResources().getString(R.string.error_code_22);
                break;
            case 23:
                message = mContext.getResources().getString(R.string.error_code_23);
                break;
            case 24:
                message = mContext.getResources().getString(R.string.error_code_24);
                break;
            case 25:
                message = mContext.getResources().getString(R.string.error_code_25);
                break;
            case 26:
                message = mContext.getResources().getString(R.string.error_code_26);
                break;
            case 27:
                message = mContext.getResources().getString(R.string.error_code_27);
                break;
            case 28:
                message = mContext.getResources().getString(R.string.error_code_28);
                break;
            case 29:
                message = mContext.getResources().getString(R.string.error_code_29);
                break;
            case 30:
                message = mContext.getResources().getString(R.string.error_code_30);
                break;
            case 31:
                message = mContext.getResources().getString(R.string.error_code_31);
                break;
            case 32:
                message = mContext.getResources().getString(R.string.error_code_32);
                break;
            case 33:
                message = mContext.getResources().getString(R.string.error_code_33);
                break;
            case 34:
                message = mContext.getResources().getString(R.string.error_code_34);
                break;
            case 35:
                message = mContext.getResources().getString(R.string.error_code_35);
                break;
            case 36:
                message = mContext.getResources().getString(R.string.error_code_36);
                break;
            case 37:
                message = mContext.getResources().getString(R.string.error_code_37);
                break;
            case 38:
                message = mContext.getResources().getString(R.string.error_code_38);
                break;
            case 39:
                message = mContext.getResources().getString(R.string.error_code_38);
                break;
            case 40:
                message = mContext.getResources().getString(R.string.error_code_40);
                break;


            case 1011:
                blockApplicationAlert(mContext, mContext.getResources().getString(R.string.error_code_block));
                return;

        }

        showAlertMessage(mContext, message);
    }


    public static void showAlertMessage(final Context mContext, String s) {


        try {
            dissmissDialog();
            alertDialog = new Dialog(mContext);

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_one_button);
            applyTypeFace(mContext, (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(s);

            Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlertMessage(final Context mContext, String s, final boolean isOktoBack) {


        try {
            dissmissDialog();
            alertDialog = new Dialog(mContext);

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_one_button);
            applyTypeFace(mContext, (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(s);

            Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    if (isOktoBack) {

                        if(mContext instanceof ChangePasswordActivity){
                            ((ChangePasswordActivity) mContext).onBackPressed();
                        }else {
                            ((BaseActivity) mContext).onBackPressed();
                        }
                    }
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showAlertMessage(final Context mContext, Throwable throwable) {


        if (throwable instanceof TimeoutException) {
            showAlertMessage(mContext, mContext.getResources().getString(R.string.error_retrofit_request_timeout));
        } else if (throwable instanceof NetworkOnMainThreadException) {
            showAlertMessage(mContext, mContext.getResources().getString(R.string.error_retrofit_network_error));
        } else if (throwable instanceof ConnectException) {
            showAlertMessage(mContext, mContext.getResources().getString(R.string.error_retrofit_connection));
        } else {
            throwable.printStackTrace();
            showAlertMessage(mContext, 1);
        }
    }


    public static void blockApplicationAlert(final Context context, String message) {
        final Dialog alertDialog = new Dialog(context);

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_one_button);

        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        applyTypeFace(context, (LinearLayout) alertDialog.findViewById(R.id.base_layout));

        TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
        txt.setText(message);

        Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
        dialogButton.setText("Update");
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        alertDialog.show();
    }

    public static void dissmissDialog() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public static void imageSet(Context context, String url, ImageView imageView) {

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);


    }

    /*
    * Default animation in bottom navigation view remove
    * */
    public static void removeShiftModeInBottomNavigation(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    public static String getTextWithBullet(String s) {

        StringTokenizer st = new StringTokenizer(
                s, "|");

        StringBuffer temp = new StringBuffer("");
        while (st.hasMoreTokens()) {
            temp.append("&#8226; " + st.nextToken() + "<br/>");
        }
        //last item remove <br/> tag
        temp.setLength(temp.length() - 5);
        return temp.toString();
    }

    public static Bitmap getRoundBitmap(String color) {
        Bitmap bitmap = Bitmap.createBitmap(
                160, // Width
                160, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background
//        canvas.drawColor(Color.TRANSPARENT);
        // Initialize a new Paint instance to draw the Circle
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);

        // Calculate the available radius of canvas
        int radius = Math.min(canvas.getWidth(), canvas.getHeight() / 2);

        // Set a pixels value to padding around the circle
        int padding = 5;

        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getHeight() / 2, // cy
                radius - padding, // Radius
                paint // Paint
        );
        return bitmap;
    }

    public static int getApkVersion() {
        int versionCode = BuildConfig.VERSION_CODE;
        return versionCode;
    }


    public static int getScreenHeight(Activity mActivity) {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return height;
    }

}
