package com.pulse.brag.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.ui.activities.BaseActivity;
import com.pulse.brag.ui.profile.UserProfileActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;


/**
 * Created by alpesh.rathod on 2/7/2018.
 */

public class AlertUtils {

    private AlertUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static Dialog createOkErrorAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.dialog_action_ok, null);
        return alertDialog.create();
    }

    public static Dialog createOkErrorAlertDialog(Context context,
                                                  @StringRes int titleResource,
                                                  @StringRes int messageResource) {

        return createOkErrorAlertDialog(context,
                context.getString(titleResource),
                context.getString(messageResource));
    }

    public static Dialog createGenericErrorAlertDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_error_title))
                .setMessage(message)
                .setNeutralButton(R.string.dialog_action_ok, null);
        return alertDialog.create();
    }

    public static Dialog createGenericErrorDialog(Context context, @StringRes int messageResource) {
        return createGenericErrorAlertDialog(context, context.getString(messageResource));
    }

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static ProgressDialog createProgressDialog(Context context,
                                                      @StringRes int messageResource) {
        return createProgressDialog(context, context.getString(messageResource));
    }
    /*=========================================brag app utility=============================*/

    public static Dialog alertDialog;

    public static void showAlertMessage(final Context mContext, Integer code, String errormessage) {

        if (code != null) {
            String message = "";
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
                case 5001:
                    message = mContext.getResources().getString(R.string.error_code_5001);
                    break;


            }
            showAlertMessage(mContext, message);
        } else if (errormessage != null && !errormessage.isEmpty()) {
            showAlertMessage(mContext, errormessage);
        } else {
            showAlertMessage(mContext, 1, null);
        }


    }


    public static void showAlertMessage(final Context mContext, String s) {


        try {
            dissmissDialog();
            alertDialog = new Dialog(mContext);

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_one_button);
            Utility.applyTypeFace(mContext, (LinearLayout) alertDialog.findViewById(R.id.base_layout));
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
            Utility.applyTypeFace(mContext, (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(s);

            Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    if (isOktoBack) {

                        if (mContext instanceof UserProfileActivity) {
                            ((UserProfileActivity) mContext).onBackPressed();
                        } else {
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


        if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
            showAlertMessage(mContext, mContext.getResources().getString(R.string.error_retrofit_request_timeout));
        } else if (throwable instanceof NetworkOnMainThreadException) {
            showAlertMessage(mContext, mContext.getResources().getString(R.string.error_retrofit_network_error));
        } else if (throwable instanceof ConnectException) {
            showAlertMessage(mContext, mContext.getResources().getString(R.string.error_retrofit_connection));
        } else {
            throwable.printStackTrace();
            showAlertMessage(mContext, 1, null);
        }
    }

    public static void blockApplicationAlert(final Context context, String message) {
        final Dialog alertDialog = new Dialog(context);

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_one_button);

        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        Utility.applyTypeFace(context, (LinearLayout) alertDialog.findViewById(R.id.base_layout));

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
}
