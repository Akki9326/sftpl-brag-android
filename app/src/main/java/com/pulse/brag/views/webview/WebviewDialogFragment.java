package com.pulse.brag.views.webview;


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

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.DialogWebviewBinding;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 26-12-2017.
 */


public class WebviewDialogFragment extends CoreDialogFragment<DialogWebviewBinding, WebviewDialogViewModel> implements WebviewDialogNavigator {

    @Inject
    WebviewDialogViewModel mWebviewDialogViewModel;

    DialogWebviewBinding mDialogWebviewBinding;


    /*View mView;
    Toolbar mToolbar;
    TextView mTxtTitle, mTxtSubtitle;
    ImageView mImgClose;
    WebView mWebView;
    ProgressBar progressBar;*/

    /*@Nullable
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

        Utility.applyTypeFace(getActivity(), (RelativeLayout) mView.findViewById(R.id.base_layout));

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
                progressBar.setVisibility(View.INVISIBLE);
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
    }*/

    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            showData();
        } else {
            //Utility.showAlertMessage(getActivity(), 0, null);
            AlertUtils.showAlertMessage(getActivity(), 0, null);
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
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounder_corner_solid_white);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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