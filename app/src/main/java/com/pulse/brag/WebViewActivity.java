package com.pulse.brag;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.callback.BaseInterface;
import com.pulse.brag.views.CustomProgressDialog;

public class WebViewActivity extends AppCompatActivity implements BaseInterface {


    Toolbar mToolbar;
    TextView mTxtTitle, mTxtSubtitle;
    ImageView mImgClose;
    WebView mWebView;

    CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        initializeData();
        setToolbar();
        setListeners();
        checkInternet();
        showData();

    }

    private void checkInternet() {
        if (Utility.isConnection(this)) {
            showData();
        } else {
            //Utility.showAlertMessage(this, 0, null);
            AlertUtils.showAlertMessage(this, 0, null);
        }
    }

    @Override
    public void setToolbar() {

        if (getIntent().hasExtra(Constants.BUNDLE_TITLE))
            mTxtTitle.setText(getIntent().getStringExtra(Constants.BUNDLE_TITLE));
        if (getIntent().hasExtra(Constants.BUNDLE_SUBTITLE))
            mTxtSubtitle.setText(getIntent().getStringExtra(Constants.BUNDLE_SUBTITLE));

    }

    @Override
    public void initializeData() {
        mToolbar = findViewById(R.id.toolbar_webview);
        mTxtTitle = mToolbar.findViewById(R.id.textview_title);
        mTxtSubtitle = mToolbar.findViewById(R.id.textview_subtitle);
        mImgClose = mToolbar.findViewById(R.id.imageview_close);

        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mProgressDialog = new CustomProgressDialog(WebViewActivity.this);

        Utility.applyTypeFace(this, (LinearLayout) findViewById(R.id.base_layout));
    }

    @Override
    public void setListeners() {

        mImgClose.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
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
        mWebView.loadUrl(getIntent().getStringExtra(Constants.BUNDLE_SUBTITLE));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
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
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide("");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
