package com.pulse.brag.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pulse.brag.R;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.respones.ChangePasswordRespone;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class CreatePasswordFragment extends BaseFragment implements BaseInterface {

    View mView;
    private EditText mEdtOldPass;
    private TextView mTxtNewPass;
    private EditText mEdtNewPass;
    private EditText mEdtConfirmPass;
    private TextView mTxtDone;

    boolean isFromChangePass;
    String mobile;
    String otp;

    public static CreatePasswordFragment newInstance(String mobile, String otp) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        args.putString(Constants.BUNDLE_OTP, otp);
        CreatePasswordFragment fragment = new CreatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_create_password, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {
        mTxtNewPass = (TextView) mView.findViewById(R.id.textview_new_pass);
        mEdtNewPass = (EditText) mView.findViewById(R.id.edittext_password);
        mEdtConfirmPass = (EditText) mView.findViewById(R.id.edittext_confirm_password);
        mTxtDone = (TextView) mView.findViewById(R.id.textview_done);
        mobile = getArguments().getString(Constants.BUNDLE_MOBILE);
        otp = getArguments().getString(Constants.BUNDLE_OTP);

    }

    @Override
    public void setListeners() {

        mTxtDone.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                validationAndAPI();
            }
        });


    }

    private void validationAndAPI() {

        if (isFromChangePass && Validation.isEmpty(mEdtOldPass)) {
            Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_old_pass));
        } else if (Validation.isEmpty(mEdtNewPass)) {
            Utility.showAlertMessage(getActivity(), getString(R.string.error_new_pass));
        } else if (Validation.isEmpty(mEdtConfirmPass)) {
            Utility.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
        } else if (!(mEdtNewPass.getText().toString().equals(mEdtConfirmPass.getText().toString()))) {
            Utility.showAlertMessage(getActivity(), getString(R.string.error_password_not_match));
        } else if (Utility.isConnection(getActivity())) {
            ChangePasswordAPI();
        } else {
            Utility.showAlertMessage(getActivity(), 0);
        }
    }


    private void ChangePasswordAPI() {
        showProgressDialog();
        ChangePasswordRequest resetPassword = new ChangePasswordRequest();
        resetPassword.setMobileNumber(mobile);
        resetPassword.setOtp(Integer.parseInt(otp));
        resetPassword.setPassword(mEdtNewPass.getText().toString());
        Call<ChangePasswordRespone> mChangePasswordResponeCall = ApiClient.getInstance(getActivity()).getApiResp().resetPassword(resetPassword);
        mChangePasswordResponeCall.enqueue(new Callback<ChangePasswordRespone>() {
            @Override
            public void onResponse(Call<ChangePasswordRespone> call, Response<ChangePasswordRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    ChangePasswordRespone respone = response.body();
                    if (respone.isStatus()) {
                        showAlertMessage();
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordRespone> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    private void showAlertMessage() {


        try {
            final Dialog alertDialog = new Dialog(getContext());

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_one_button);
            Utility.applyTypeFace(getContext(), (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(getString(R.string.msg_password_change_successfull));

            Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    ((SplashActivty) getActivity()).popBackToLogin();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void showData() {

    }
}
