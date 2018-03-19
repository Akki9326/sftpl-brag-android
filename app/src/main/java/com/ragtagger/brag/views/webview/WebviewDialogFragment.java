package com.ragtagger.brag.views.webview;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.DialogWebviewBinding;
import com.ragtagger.brag.ui.core.CoreDialogFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 26-12-2017.
 */


public class WebviewDialogFragment extends CoreDialogFragment<DialogWebviewBinding, WebviewDialogViewModel> implements WebviewDialogNavigator {

    @Inject
    WebviewDialogViewModel mWebviewDialogViewModel;

    DialogWebviewBinding mDialogWebviewBinding;


    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            showData();
        } else {
            //Utility.showAlertMessage(getActivity(), 0, null);
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
        mWebviewDialogViewModel.setNavigator(this);
    }

    @Override
    public Dialog onCreateFragmentDialog(Bundle savedInstanceState, Dialog dialog) {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounder_corner_solid_white);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(getResources().getColor(R.color.gray_transparent));
        }*/
        return dialog;
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mDialogWebviewBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), mDialogWebviewBinding.baseLayout);

        mDialogWebviewBinding.webview.getSettings().setJavaScriptEnabled(true);

        mDialogWebviewBinding.progress.setMax(100);
        mDialogWebviewBinding.progress.getProgressDrawable().setColorFilter(getResources().getColor(R.color.pink), PorterDuff.Mode.SRC_IN);
        showData();
    }

    @Override
    public void setUpToolbar() {
        if (getArguments().containsKey(Constants.BUNDLE_TITLE))
            mWebviewDialogViewModel.updateTitle(getArguments().getString(Constants.BUNDLE_TITLE));
        if (getArguments().containsKey(Constants.BUNDLE_SUBTITLE))
            mWebviewDialogViewModel.updateSubTitle(getArguments().getString(Constants.BUNDLE_SUBTITLE));
    }

    @Override
    public WebviewDialogViewModel getViewModel() {
        return mWebviewDialogViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_webview;
    }

    public void setProgressBar(int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDialogWebviewBinding.progress.setProgress(index, true);
        } else {
            mDialogWebviewBinding.progress.setProgress(index);
        }
    }

    public void showData() {

        mDialogWebviewBinding.webview.loadUrl(getArguments().getString(Constants.BUNDLE_SUBTITLE));
        mDialogWebviewBinding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                setProgressBar(0);
                mDialogWebviewBinding.progress.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                setProgressBar(100);
                mDialogWebviewBinding.progress.setVisibility(View.INVISIBLE);
            }
        });
        mDialogWebviewBinding.webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                setProgressBar(newProgress);
            }
        });
    }

    @Override
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

    }

    @Override
    public void close() {
        dismissDialog("");
    }


}