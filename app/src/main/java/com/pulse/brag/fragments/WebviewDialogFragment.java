package com.pulse.brag.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.WebViewActivity;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.views.CustomProgressDialog;
import com.pulse.brag.views.OnSingleClickListener;

/**
 * Created by nikhil.vadoliya on 26-12-2017.
 */


public class WebviewDialogFragment extends DialogFragment implements BaseInterface {


    View mView;
    Toolbar mToolbar;
    TextView mTxtTitle, mTxtSubtitle;
    ImageView mImgClose;
    WebView mWebView;

    CustomProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_webview, container, false);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeData();
        setToolbar();
        setListeners();
        checkInternet();
        showData();
    }

    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            showData();
        } else {
            Utility.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void setToolbar() {
        if (getArguments().containsKey(Constants.BUNDLE_TITLE))
            mTxtTitle.setText(getArguments().getString(Constants.BUNDLE_TITLE));
        if (getArguments().containsKey(Constants.BUNDLE_SUBTITLE))
            mTxtSubtitle.setText(getArguments().getString(Constants.BUNDLE_SUBTITLE));
    }

    @Override
    public void initializeData() {

        mToolbar = mView.findViewById(R.id.toolbar_webview);
        mTxtTitle = mToolbar.findViewById(R.id.textview_title);
        mTxtSubtitle = mToolbar.findViewById(R.id.textview_subtitle);
        mImgClose = mToolbar.findViewById(R.id.imageview_close);


        mWebView = mView.findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mProgressDialog = new CustomProgressDialog(getActivity());

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));

    }

    @Override
    public void setListeners() {

        mImgClose.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();

            }
        });
    }

    @Override
    public void showData() {

        showProgressDialog();
        mWebView.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest url) {
                view.loadData(url.getUrl().toString(), "text/html", "UTF-8");
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                hideProgressDialog();
//                hideProgressDialog();
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(getArguments().getString(Constants.BUNDLE_SUBTITLE));

    }

    public void showProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    hideProgressDialog();
                    mProgressDialog.show("");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss("");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounder_corner_solid_white);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
}
