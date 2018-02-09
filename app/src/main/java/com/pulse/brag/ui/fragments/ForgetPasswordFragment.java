package com.pulse.brag.ui.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.ui.activities.ChangePasswordOrMobileActivity;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.enums.OTPValidationIsFrom;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class ForgetPasswordFragment extends BaseFragment implements BaseInterface {

    View mView;
    EditText mEdtMobile;
    TextView mTxtSendOtp, mTxtMobileNum;


    public static ForgetPasswordFragment newInstance(String mobile) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_forget_pass, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
        if (getActivity() instanceof ChangePasswordOrMobileActivity) {
            ((ChangePasswordOrMobileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_change_mobile_num));
        }
    }

    @Override
    public void initializeData() {

        mEdtMobile = (EditText) mView.findViewById(R.id.edittext_mobile_num);
        mTxtSendOtp = (TextView) mView.findViewById(R.id.textview_send_otp);
        mTxtMobileNum = (TextView) mView.findViewById(R.id.textview_mobile_num);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_MOBILE)) {
            if (getArguments().getString(Constants.BUNDLE_MOBILE).trim().isEmpty()
                    || getArguments().getString(Constants.BUNDLE_MOBILE).length() < 10) {
                mEdtMobile.setText("");
            } else {
                mEdtMobile.setText(getArguments().getString(Constants.BUNDLE_MOBILE));
            }
        }
        if (getActivity() instanceof ChangePasswordOrMobileActivity) {
            mEdtMobile.setHint(getString(R.string.hint_new_mobile_num));
            mTxtMobileNum.setText(getString(R.string.label_new_mobile_no));
        }

    }

    @Override
    public void setListeners() {

        mTxtSendOtp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (Validation.isEmpty(mEdtMobile)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (!Validation.isValidMobileNum(mEdtMobile)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Utility.isConnection(getActivity())) {
                    ForgetPassAPI(mEdtMobile.getText().toString());
                } else {
                    Utility.showAlertMessage(getActivity(), 0, null);
                }
            }
        });
    }

    private void ForgetPassAPI(String s) {
        showProgressDialog();
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<GeneralResponse> responeCall = ApiClient.getInstance(getActivity()).getApiResp().resendOtp(s);
        responeCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralResponse respone = response.body();
                    if (respone.isStatus()) {
                        // TODO: 13-11-2017 email address pass for display in otp screen
                        if (getActivity() instanceof SplashActivity) {
                            ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mEdtMobile.getText().toString(),
                                    "email@email.com", OTPValidationIsFrom.FORGET_PASS.ordinal()),
                                    true, true, "OTP_frag");
                        } else {
                            ((ChangePasswordOrMobileActivity) getActivity()).pushFragmentInChangeContainer(OTPFragment.newInstance(mEdtMobile.getText().toString(),
                                    "email@email.com", OTPValidationIsFrom.CHANGE_MOBILE.ordinal()),
                                    true, true, "OTP_frag");
                        }

                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    @Override
    public void showData() {

    }
}
