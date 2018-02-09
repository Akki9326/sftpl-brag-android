package com.pulse.brag.ui.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.ui.activities.ChangePasswordOrMobileActivity;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.enums.OTPValidationIsFrom;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.response.OTPVerifyResponse;
import com.pulse.brag.views.OnSingleClickListener;
import com.pulse.brag.views.PinView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class OTPFragment extends BaseFragment implements BaseInterface {

    View mView;
    PinView mPinView;
    TextView mTxtVerify;
    TextView mTxtEmailMsg, mTxtResend;

    String mobileNum;
    String emailAddress;

    //is from signup or forget pass
    int isFromScreen;

    public static OTPFragment newInstance(String mobilenum, String email, int isFromScreen) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobilenum);
        args.putString(Constants.BUNDLE_EMAIL, email);
        args.putInt(Constants.BUNDLE_IS_FROM_SIGNUP, isFromScreen);
        OTPFragment fragment = new OTPFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_otp, container, false);
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
        mPinView = (PinView) mView.findViewById(R.id.pinView);
        mTxtVerify = (TextView) mView.findViewById(R.id.textview_verify);
        mTxtEmailMsg = (TextView) mView.findViewById(R.id.textView_label_otp);
        mTxtResend = (TextView) mView.findViewById(R.id.textview_resend);
        mobileNum = getArguments().getString(Constants.BUNDLE_MOBILE);
        emailAddress = getArguments().getString(Constants.BUNDLE_EMAIL);
        isFromScreen = getArguments().getInt(Constants.BUNDLE_IS_FROM_SIGNUP);

        mPinView.setLineColor(getResources().getColor(R.color.pink));
        mPinView.setAnimationEnable(true);
        mPinView.requestFocus();
    }

    @Override
    public void setListeners() {

        mTxtVerify.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {


                if (mPinView.getText().toString().length() < 6) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_otp));
                } else if (Utility.isConnection(getActivity())) {
                    OTPVerifyAPI(mPinView.getText().toString());
                } else {
                    Utility.showAlertMessage(getActivity(), 0,null);
                }
            }
        });
        mPinView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtVerify.performClick();
                    return true;
                }
                return false;
            }
        });
        mTxtResend.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (Utility.isConnection(getActivity())) {
                    ResendOTPAPI();
                } else {
                    Utility.showAlertMessage(getActivity(), 0,null);
                }
            }
        });
    }

    private void ResendOTPAPI() {
        showProgressDialog();
        Call<GeneralResponse> responeCall = ApiClient.getInstance(getActivity()).getApiResp().resendOtp(mobileNum);
        responeCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralResponse respone = response.body();
                    if (respone.isStatus()) {


                    } else {
                        Utility.showAlertMessage(getActivity(),respone.getErrorCode(), respone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1,null);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    private void OTPVerifyAPI(final String otp) {
        showProgressDialog();

        Call<OTPVerifyResponse> mOtpVerifyResponeCall = null;
        switch (OTPValidationIsFrom.values()[isFromScreen]) {
            case SIGN_UP:
                mOtpVerifyResponeCall = ApiClient.getInstance(getActivity()).getApiResp().verifyOtp(mobileNum, otp);
                break;
            case FORGET_PASS:
                mOtpVerifyResponeCall = ApiClient.getInstance(getActivity()).getApiResp().verifyOtpForgetPass(mobileNum, otp);
                break;
            case CHANGE_MOBILE:
                mOtpVerifyResponeCall = ApiClient.getInstance(getActivity()).getApiResp().verifyOtp(mobileNum, otp);
                break;
        }


        mOtpVerifyResponeCall.enqueue(new Callback<OTPVerifyResponse>() {
            @Override
            public void onResponse(Call<OTPVerifyResponse> call, Response<OTPVerifyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    OTPVerifyResponse respone = response.body();
                    if (respone.isStatus()) {

                        switch (OTPValidationIsFrom.values()[isFromScreen]) {
                            case CHANGE_MOBILE:

                                ((ChangePasswordOrMobileActivity) getActivity()).pushFragmentInChangeContainer(ChangeMobileNumberFragment.newInstance(mobileNum)
                                        , true, true, "");
                                break;
                            case FORGET_PASS:
                                ((SplashActivity) getActivity()).pushFragments(CreatePasswordFragment.newInstance(mobileNum, otp),
                                        true, true, "Create_Pass_Frag");
                                break;

                            case SIGN_UP:
                                ((SplashActivity) getActivity()).pushFragments(new SignUpComplateFragment(),
                                        true, true, "Signup_Complete_Frag");
                                break;
                        }
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode(),respone.getMessage());
                    }

                } else {
                    Utility.showAlertMessage(getActivity(), 1,null);
                }
            }

            @Override
            public void onFailure(Call<OTPVerifyResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    @Override
    public void showData() {
        mTxtEmailMsg.setText(getString(R.string.msg_otp_label) + " " + emailAddress);
    }

    private void showAlertMessage(String message) {
        try {

            final Dialog alertDialog = new Dialog(getActivity());

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_one_button);
            Utility.applyTypeFace(getActivity(), (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(message);

            Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    ((ChangePasswordOrMobileActivity) getActivity()).finish();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
