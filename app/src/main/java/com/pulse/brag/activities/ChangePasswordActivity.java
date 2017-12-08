package com.pulse.brag.activities;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.fragments.HomeFragment;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.respones.ChangePasswordRespone;
import com.pulse.brag.views.CustomProgressDialog;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 20-11-2017.
 */


public class ChangePasswordActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private ImageView mImgBack;
    private EditText mEdtOldPass;
    private TextView mTxtNewPass;
    private EditText mEdtNewPass;
    private EditText mEdtConfirmPass;
    private TextView mTxtDone;

    CustomProgressDialog mProgressDialog;

    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_change_passoword);

        init();
        setListener();

    }

    private void setListener() {

        mImgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });

        mTxtDone.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                validationAndAPI();
            }
        });

        mEdtConfirmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtDone.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private void validationAndAPI() {

        if (Validation.isEmpty(mEdtOldPass)) {
            Utility.showAlertMessage(this, getString(R.string.error_enter_old_pass));
        } else if (Validation.isEmpty(mEdtNewPass)) {
            Utility.showAlertMessage(this, getString(R.string.error_new_pass));
        } else if (Validation.isEmpty(mEdtConfirmPass)) {
            Utility.showAlertMessage(this, getString(R.string.error_confirm_pass));
        } else if (!(mEdtNewPass.getText().toString().equals(mEdtConfirmPass.getText().toString()))) {
            Utility.showAlertMessage(this, getString(R.string.error_password_not_match));
        } else if (Utility.isConnection(this)) {
            ChangePasswordAPI();
        } else {
            Utility.showAlertMessage(this, 0);
        }
    }

    private void ChangePasswordAPI() {

        showProgressDialog();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setMobileNumber(mobile);
        changePasswordRequest.setOldPassword(mEdtOldPass.getText().toString());
        changePasswordRequest.setPassword(mEdtNewPass.getText().toString());
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1");
        Call<ChangePasswordRespone> mChangePasswordResponeCall = ApiClient.getInstance(ChangePasswordActivity.this).getApiResp().changePassword(changePasswordRequest);
        mChangePasswordResponeCall.enqueue(new Callback<ChangePasswordRespone>() {
            @Override
            public void onResponse(Call<ChangePasswordRespone> call, Response<ChangePasswordRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    ChangePasswordRespone respone = response.body();
                    if (respone.isStatus()) {
                        Utility.showAlertMessage(ChangePasswordActivity.this, getString(R.string.msg_password_change_successfull), true);
                    } else {
                        Utility.showAlertMessage(ChangePasswordActivity.this, respone.getErrorCode());
                    }
                } else {
                    Utility.showAlertMessage(ChangePasswordActivity.this, 1);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordRespone> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(ChangePasswordActivity.this, t);
            }
        });
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_change_pass);
        mImgBack = (ImageView) mToolbar.findViewById(R.id.imageView_back);
        mEdtOldPass = (EditText) findViewById(R.id.edittext_old_password);
        mTxtNewPass = (TextView) findViewById(R.id.textview_new_pass);
        mEdtNewPass = (EditText) findViewById(R.id.edittext_password);
        mEdtConfirmPass = (EditText) findViewById(R.id.edittext_confirm_password);
        mTxtDone = (TextView) findViewById(R.id.textview_done);

        Utility.applyTypeFace(getApplicationContext(), (LinearLayout) findViewById(R.id.base_layout));

        mProgressDialog = new CustomProgressDialog(this);

        if (getIntent().hasExtra(Constants.BUNDLE_MOBILE))
            mobile = getIntent().getStringExtra(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    public void showProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing())
                    hideProgressDialog();
                mProgressDialog.show("");
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

        }

    }
}
