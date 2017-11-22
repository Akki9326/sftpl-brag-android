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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralRespone;
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
    TextView mTxtSendOtp;


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
    public void setToolbar() {

    }

    @Override
    public void initializeData() {

        mEdtMobile = (EditText) mView.findViewById(R.id.edittext_mobile_num);
        mTxtSendOtp = (TextView) mView.findViewById(R.id.textview_send_otp);

        if (getArguments().containsKey(Constants.BUNDLE_MOBILE)) {
            if (getArguments().getString(Constants.BUNDLE_MOBILE).trim().isEmpty()
                    || getArguments().getString(Constants.BUNDLE_MOBILE).length() < 10) {
                mEdtMobile.setText("");
            } else {
                mEdtMobile.setText(getArguments().getString(Constants.BUNDLE_MOBILE));
            }
        }
    }

    @Override
    public void setListeners() {

        mTxtSendOtp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (Validation.isEmpty(mEdtMobile)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (mEdtMobile.getText().toString().length() < 10) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Utility.isConnection(getActivity())) {
                    ForgetPassAPI(mEdtMobile.getText().toString());
                } else {
                    Utility.showAlertMessage(getActivity(), 0);
                }
            }
        });
    }

    private void ForgetPassAPI(String s) {
        showProgressDialog();
        Call<GeneralRespone> responeCall = ApiClient.getInstance(getActivity()).getApiResp().resendOtp(s);
        responeCall.enqueue(new Callback<GeneralRespone>() {
            @Override
            public void onResponse(Call<GeneralRespone> call, Response<GeneralRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralRespone respone = response.body();
                    if (respone.isStatus()) {
                        // TODO: 13-11-2017 email address pass for disply in otp screen
                        ((SplashActivty) getActivity()).pushFragments(OTPFragment.newInstance(mEdtMobile.getText().toString(), "email@email.com", false), true, true, "OTP_frag");
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<GeneralRespone> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    @Override
    public void showData() {

    }
}
