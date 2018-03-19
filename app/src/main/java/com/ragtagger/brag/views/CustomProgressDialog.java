package com.ragtagger.brag.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.ragtagger.brag.R;




/*
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by meet.parabiya on 6/15/2015.
 */

/**
 * Class Usage : Custom Progress dialog which is in circle shape background and in orange color.
 */


public class CustomProgressDialog extends Dialog {
    MaterialProgressBar progress1;

    Context mContext;
    CustomProgressDialog dialog;

    public CustomProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomProgressDialog show(CharSequence message) {

        dialog = new CustomProgressDialog(mContext, R.style.ProgressDialog);
        dialog.setContentView(R.layout.view_material_progress);

        progress1 = (MaterialProgressBar) dialog.findViewById(R.id.progress1);


        //progress1.setColorSchemeResources(R.color.red,R.color.green,R.color.blue,R.color.orange);
        progress1.setColorSchemeResources(R.color.pink);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.spinnerImageView);
//        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
//        spinner.start();
//
//		if(message == null || message.length() == 0) {
//			dialog.findViewById(R.id.message).setVisibility(View.GONE);
//		} else {
//			TextView txt = (TextView)dialog.findViewById(R.id.message);
//			txt.setText(message);
//		}
        dialog.setCancelable(false);
        //dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        //noinspection deprecation
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
        if (dialog != null) {
            dismiss();
            dialog.show();
//            progress1.setVisibility(View.VISIBLE);
        }
        return dialog;
    }

    public CustomProgressDialog dismiss(CharSequence message) {
        if (dialog != null) {
            dialog.dismiss();
//            progress1.setVisibility(View.INVISIBLE);
        }

        return dialog;

    }

    public CustomProgressDialog hide(CharSequence message) {
        if (dialog != null) {
            dialog.hide();
        }
        return dialog;
    }
}
