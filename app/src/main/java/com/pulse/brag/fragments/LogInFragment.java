package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.pulse.brag.activities.MainActivity;
import com.pulse.brag.R;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class LogInFragment extends BaseFragment implements BaseInterface {


    View mView;
    TextView mTxtLogin, mTxtSignUp, mTxtForget;
    ImageView mImgPass;
    EditText mEdtNumber, mEdtPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_login, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void setToolbar() {
    }

    @Override
    public void initializeData() {
        mTxtLogin = (TextView) mView.findViewById(R.id.textview_login);
        mTxtSignUp = (TextView) mView.findViewById(R.id.textview_signup);
        mImgPass = (ImageView) mView.findViewById(R.id.imageview_pass_visible);
        mEdtPassword = (EditText) mView.findViewById(R.id.edittext_password);
        mEdtNumber = (EditText) mView.findViewById(R.id.edittext_mobile_num);
        mTxtForget = (TextView) mView.findViewById(R.id.textview_forget);

        // TODO: 08-11-2017 Login username and password
//        mEdtNumber.setText("7874487853");
//        mEdtPassword.setText("sailfin*123");
    }

    @Override
    public void setListeners() {

        mTxtLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (Validation.isEmpty(mEdtNumber)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (!Validation.isValidMobileNum(mEdtNumber)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Validation.isEmpty(mEdtPassword)) {
                    Utility.showAlertMessage(getActivity(), getResources().getString(R.string.error_pass));
                } else if (Utility.isConnection(getActivity())) {
                    LoginAPICall(mEdtNumber.getText().toString(), mEdtPassword.getText().toString());
                } else {
                    Utility.showAlertMessage(getActivity(), 0);
                }
            }
        });

        mTxtSignUp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ((SplashActivty) getActivity()).pushFragments(new SignUpFragment(), true, true, "Signup_Frag");
            }
        });

        mEdtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtLogin.performClick();
                    return true;
                }
                return false;
            }
        });

        mImgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImgPass.isSelected()) {
                    mImgPass.setSelected(false);
                    mEdtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEdtPassword.setSelection(mEdtPassword.getText().length());
                } else {
                    mImgPass.setSelected(true);
                    mEdtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEdtPassword.setSelection(mEdtPassword.getText().length());
                }
            }
        });
        mTxtForget.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                ((SplashActivty) getActivity()).pushFragments(ForgetPasswordFragment.newInstance(mEdtNumber.getText().toString()), true, true, "Forget_Frag");

            }
        });
    }

    private void LoginAPICall(String mobile, String password) {

        showProgressDialog();
        LoginRequest loginRequest = new LoginRequest(mobile, password);
        Call<LoginResponse> mLoginResponeCall = ApiClient.getInstance(getActivity()).getApiResp().userLogin(loginRequest);
        mLoginResponeCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    LoginResponse respone = response.body();
                    okhttp3.Headers headers = response.headers();
                    if (respone.isStatus()) {

                        setHeaderInPref(headers);
                        PreferencesManager.getInstance().setIsLogin(true);
                        PreferencesManager.getInstance().setUserData(new Gson().toJson(respone.getData()));
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    private void setHeaderInPref(okhttp3.Headers headers) {

        PreferencesManager.getInstance().setAccessToken(headers.get("access_token"));
        PreferencesManager.getInstance().setDeviceToken(FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    public void showData() {
        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
    }
}
