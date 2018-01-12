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
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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


    ProgressBar progressBar;

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

        progressBar = mView.findViewById(R.id.progress);
        progressBar.setMax(100);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.pink), PorterDuff.Mode.SRC_IN);

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

        mWebView.loadUrl(getArguments().getString(Constants.BUNDLE_SUBTITLE));


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                setProgressBar(0);
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                setProgressBar(100);
                progressBar.setVisibility(View.GONE);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                setProgressBar(newProgress);
            }
        });
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

    public void setProgressBar(int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(index, true);
        } else {
            progressBar.setProgress(index);
        }
    }
}