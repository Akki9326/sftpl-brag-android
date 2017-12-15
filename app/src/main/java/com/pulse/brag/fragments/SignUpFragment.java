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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.enums.OTPValidationIsFrom;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.requests.SignInRequest;
import com.pulse.brag.pojo.response.SignUpResponse;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class SignUpFragment extends BaseFragment implements BaseInterface {

    View mView;

    EditText mEdtFirstNam, mEdtLastNam, mEdtEmail, mEdtMobile, mEdtPass, mEdtConfirmPas;
    Button mBtnSignup, mBtnLogin;
    TextView mTxtSignup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
//            getActivity().getWindow().addFlags();
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
        mTxtSignup = (TextView) mView.findViewById(R.id.textview_signup);
        mEdtFirstNam = (EditText) mView.findViewById(R.id.edittext_firstname);
        mEdtLastNam = (EditText) mView.findViewById(R.id.edittext_lastname);
        mEdtEmail = (EditText) mView.findViewById(R.id.edittext_email);
        mEdtMobile = (EditText) mView.findViewById(R.id.edittext_mobile_num);
        mEdtPass = (EditText) mView.findViewById(R.id.edittext_password);
        mEdtConfirmPas = (EditText) mView.findViewById(R.id.edittext_confirm_password);
    }

    @Override
    public void setListeners() {
        mTxtSignup.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {


                if (Validation.isEmpty(mEdtFirstNam)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_please_enter_first_name));
                } else if (Validation.isEmpty(mEdtEmail)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_please_email));
                } else if (!Validation.isEmailValid(mEdtEmail)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_email_valid));
                } else if (Validation.isEmpty(mEdtMobile)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (!Validation.isValidMobileNum(mEdtMobile)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Validation.isEmpty(mEdtPass)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_pass));
                } else if (Validation.isEmpty(mEdtConfirmPas)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
                } else if (!(mEdtPass.getText().toString().trim().equals(mEdtConfirmPas.getText().toString().trim()))) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_password_not_match));
                } else if (Utility.isConnection(getActivity())) {
                    SignInRequest signInRequest = new SignInRequest("Mr", mEdtFirstNam.getText().toString(), mEdtLastNam.getText().toString().trim(),
                            mEdtEmail.getText().toString(), mEdtMobile.getText().toString()
                            , mEdtPass.getText().toString());
                    SignInAPICall(signInRequest);
                } else {
                    Utility.showAlertMessage(getActivity(), 0, null);
                }
            }
        });

        mEdtConfirmPas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtSignup.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private void SignInAPICall(final SignInRequest signInRequest) {
        Utility.hideSoftkeyboard(getActivity());
        showProgressDialog();
        Call<SignUpResponse> mSignUpResponeCall = ApiClient.getInstance(getActivity()).getApiResp().userSignIn(signInRequest);
        mSignUpResponeCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    SignUpResponse signUpRespone = response.body();
                    if (signUpRespone.isStatus()) {

                        ((SplashActivty) getActivity()).pushFragments(OTPFragment.newInstance(mEdtMobile.getText().toString()
                                , mEdtEmail.getText().toString(), OTPValidationIsFrom.SIGN_UP.ordinal()),
                                true, true, "OTP_Frag");


                    } else {
                        Utility.showAlertMessage(getActivity(), signUpRespone.getErrorCode(), signUpRespone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });

    }

    @Override
    public void showData() {

    }
}
