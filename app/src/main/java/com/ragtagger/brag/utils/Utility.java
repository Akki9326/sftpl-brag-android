package com.ragtagger.brag.utils;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.BuildConfig;
import com.ragtagger.brag.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import static com.ragtagger.brag.utils.AppLogger.TAG;

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
            Log.i(TAG, "applyTypeFace: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getFontType(Context context, View view) {
        try {
            switch (Integer.parseInt(view.getTag().toString())) {
                case Constants.FONT_OPENSANS_REGULAR:
                    return context.getResources().getString(R.string.font_opensans_regular);
                case Constants.FONT_OPENSANS_LIGHT:
                    return context.getResources().getString(R.string.font_opensans_light);
                case Constants.FONT_OPENSANS_BOLD:
                    return context.getResources().getString(R.string.font_opensans_bold);
                case Constants.FONT_OPENSANS_SEMI_BOLD:
                    return context.getResources().getString(R.string.font_opensans_semi_bold);
                case Constants.FONT_OPENSANS_ITALIC:
                    return context.getString(R.string.font_opensans_italic);
//                case Constants.FONT_BOOK_ID:
//                    return context.getResources().getString(R.string.font_book);
//                case Constants.FONT_BOOK_ITALIC_ID:
//                    return context.getResources().getString(R.string.font_book_italic);
                default:
                    return context.getResources().getString(R.string.font_opensans_regular);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getString(R.string.font_opensans_regular);
    }

    public static void expandTextView(TextView tv) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount());
        animation.setDuration(200).start();
    }

    public static void collapseTextView(TextView tv, int numLines) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", numLines);
        animation.setDuration(200).start();
    }

    public static void imageSetCenterInside(Context context, String url, ImageView imageView, int width, int height) {

        if (url == null) {
            url = String.valueOf(R.drawable.background);
        } else if (url.isEmpty()) {
            url = String.valueOf(R.drawable.background);
        }
        Picasso.with(context)
                .load(url)
                .centerInside()
                .resize(width, height)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView);
    }


    public static void imageSetCenterInside(Context context, String url, ImageView imageView) {
        imageSetCenterInside(context, url, imageView, 1000, 1000);

    }

    public static void imageSetCenterCrop(Context context, String url, ImageView imageView) {

        if (url == null) {
            url = String.valueOf(R.drawable.background);
        } else if (url.isEmpty()) {
            url = String.valueOf(R.drawable.background);
        }
        Picasso.with(context)
                .load(url)
                .centerCrop()
                .resize(1000, 1000)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView);
    }


    /*
     * Default animation in bottom navigation view remove
     * */
    @SuppressLint("RestrictedApi")
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
        //last item_list_status remove <br/> tag
        temp.setLength(temp.length() - 5);
        return temp.toString();
    }

    public static Bitmap getRoundBitmap(String color, boolean isBorder) {
        Bitmap bitmap = Bitmap.createBitmap(
                160, // Width
                160, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background
//        canvas.drawColor(Color.BLUE);
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
                canvas.getWidth() / 2,
                canvas.getHeight() / 2,
                radius - padding, // Radius
                paint // Paint
        );


        if (isBorder) {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            float saveStrokeWidth = paint.getStrokeWidth();
            paint.setAntiAlias(false);
            paint.setDither(true);
            paint.setStrokeWidth(5);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius - (5 / 2), paint);
            paint.setStrokeWidth(saveStrokeWidth);
        }

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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String getIndianCurrencyPriceFormatWithComma(double price) {

        /*String rupeessymbol;
        String totalprice = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(new Integer(price));
        if (totalprice.contains("Rs.")) {
            rupeessymbol = "\u20B9" + totalprice.substring(3, totalprice.length()).trim();
            return rupeessymbol;
        } else {
            return totalprice.replace(" ", "");
        }*/
        DecimalFormat df = new DecimalFormat("#,###.00");
        df.setMinimumIntegerDigits(1);
        String strPrice = df.format(price);
        return "\u20B9" + strPrice;
    }

    public static String getIndianCurrencyPriceFormat(int price) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setMinimumIntegerDigits(1);
        String strPrice = df.format(price);
        return "\u20B9" + strPrice;
    }

    public static String getIndianCurrencyPriceFormat(double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setMinimumIntegerDigits(1);
        String strPrice = df.format(price);
        return "\u20B9" + strPrice;
    }

    public static String getBadgeNumber(int num) {
        if (num > 0 && num <= 99) {
            return "" + num;
        } else {
            return "99+";
        }
    }

    public static String getNotificationlabel(Activity activity) {
        if (BragApp.NotificationNumber > 0) {
            return activity.getString(R.string.toolbar_label_notification) + " (" + getBadgeNumber(BragApp.NotificationNumber) + ")";
        } else {
            return activity.getString(R.string.toolbar_label_notification);
        }
    }


    public static void makeFolder(String path, String folderName) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, folderName);
        if (!file.exists() && !file.isDirectory())
            file.mkdir();
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes) {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

    public static void fileIntentHandle(Activity activity, File file) {

        if (file.getAbsolutePath().length() != 0) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uri = FileProvider.getUriForFile(activity,
                            activity.getApplicationContext().getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, type);
                } else {
                    intent.setDataAndType(Uri.fromFile(file), type);
                }
                activity.startActivity(intent);
            } catch (ActivityNotFoundException anfe) {
                AlertUtils.showAlertMessage(activity, activity.getResources().getString(R.string.error_app_not_found));
            }

        } else {
            AlertUtils.showAlertMessage(activity, 1, null, null);
        }
    }


    public static int numLengthFromInt(int n) {
        if (n == 0) return 1;
        int l;
        n = Math.abs(n);
        for (l = 0; n > 0; ++l)
            n /= 10;
        return l;
    }

    public static float dpToPx(Context context, float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }

    public static int getListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return 0;
        }
        // get listview height
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // Change Height of ListView


        return (totalHeight + (myListView.getDividerHeight() * (adapterCount)));
    }

}
