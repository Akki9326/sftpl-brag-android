package com.pulse.brag.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralRespone;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.respones.LoginRespone;
import com.pulse.brag.pojo.respones.OTPVerifyRespone;
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
    boolean isFromSignup;

    public static OTPFragment newInstance(String mobilenum, String email, boolean isFromSignup) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobilenum);
        args.putString(Constants.BUNDLE_EMAIL, email);
        args.putBoolean(Constants.BUNDLE_IS_FROM_SIGNUP, isFromSignup);
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
        isFromSignup = getArguments().getBoolean(Constants.BUNDLE_IS_FROM_SIGNUP);

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
                    Utility.showAlertMessage(getActivity(), 0);
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
                    Utility.showAlertMessage(getActivity(), 0);
                }
            }
        });
    }

    private void ResendOTPAPI() {
        showProgressDialog();
        Call<GeneralRespone> responeCall = ApiClient.getInstance(getActivity()).getApiResp().resendOtp(mobileNum);
        responeCall.enqueue(new Callback<GeneralRespone>() {
            @Override
            public void onResponse(Call<GeneralRespone> call, Response<GeneralRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralRespone respone = response.body();
                    if (respone.isStatus()) {


                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1);
                }
            }

            @Override
            public void onFailure(Call<GeneralRespone> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    private void OTPVerifyAPI(final String otp) {
        showProgressDialog();

        Call<OTPVerifyRespone> mOtpVerifyResponeCall;
        if (isFromSignup) {
            mOtpVerifyResponeCall = ApiClient.getInstance(getActivity()).getApiResp().verifyOtp(mobileNum, otp);
        } else {
            mOtpVerifyResponeCall = ApiClient.getInstance(getActivity()).getApiResp().verifyOtpForgetPass(mobileNum, otp);
        }

        mOtpVerifyResponeCall.enqueue(new Callback<OTPVerifyRespone>() {
            @Override
            public void onResponse(Call<OTPVerifyRespone> call, Response<OTPVerifyRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    OTPVerifyRespone respone = response.body();
                    if (respone.isStatus()) {
                        if (isFromSignup) {
                            ((SplashActivty) getActivity()).pushFragments(new SignUpComplateFragment(),
                                    true, true, "Signup_Complete_Frag");
                        } else {
                            ((SplashActivty) getActivity()).pushFragments(CreatePasswordFragment.newInstance(mobileNum,otp),
                                    true, true, "Create_Pass_Frag");
                        }

                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode());
                    }

                } else {
                    Utility.showAlertMessage(getActivity(), 1);
                }
            }

            @Override
            public void onFailure(Call<OTPVerifyRespone> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    @Override
    public void showData() {
        mTxtEmailMsg.setText(getString(R.string.msg_otp_label) + " " + emailAddress);
    }
}
