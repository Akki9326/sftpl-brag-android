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

import com.google.gson.Gson;
import com.pulse.brag.R;
import com.pulse.brag.activities.ChangePasswordOrMobileActivity;
import com.pulse.brag.enums.OTPValidationIsFrom;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.requests.ChangeMobileNumberRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 11-12-2017.
 */


public class ChangeMobileNumberFragment extends BaseFragment implements BaseInterface {

    View mView;
    TextView mTxtMobileNum;
    EditText mEdtPass;
    ImageView mImgToggle;
    TextView mTxtDone;

    String mobilenum;

    public static ChangeMobileNumberFragment newInstance(String mobilenum) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobilenum);
        ChangeMobileNumberFragment fragment = new ChangeMobileNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_change_mobile_num, container, false);
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
//        ((ChangePasswordOrMobileActivity) getActivity()).showToolBar("Change Mobile Number");
    }

    @Override
    public void initializeData() {
        mTxtMobileNum = (TextView) mView.findViewById(R.id.textview_new_mobile_num);
        mEdtPass = (EditText) mView.findViewById(R.id.edittext_password);
        mImgToggle = (ImageView) mView.findViewById(R.id.imageview_pass_visible);
        mTxtDone = (TextView) mView.findViewById(R.id.textview_done);

        mobilenum = getArguments().getString(Constants.BUNDLE_MOBILE);
        mTxtMobileNum.setText(mobilenum);

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
    }

    @Override
    public void setListeners() {


        mTxtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationAndAPICall();
            }
        });
        mImgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImgToggle.isSelected()) {
                    mImgToggle.setSelected(false);
                    mEdtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEdtPass.setSelection(mEdtPass.getText().length());
                } else {
                    mImgToggle.setSelected(true);
                    mEdtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEdtPass.setSelection(mEdtPass.getText().length());
                }
            }
        });
        mEdtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    private void validationAndAPICall() {
        if (Validation.isEmpty(mEdtPass)) {
            Utility.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            ChangeMobileNumberAPICall();
        } else {
            Utility.showAlertMessage(getActivity(), 0);
        }
    }

    private void ChangeMobileNumberAPICall() {


        // TODO: 13-12-2017 if you display mobile number in more Fragment than isStatus==true update mobile number display
        showProgressDialog();
        ChangeMobileNumberRequest changeMobileNumberRequest = new ChangeMobileNumberRequest(mTxtMobileNum.getText().toString()
                , mEdtPass.getText().toString());
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<GeneralResponse> mResponeCall = ApiClient.getInstance(getActivity()).getApiResp().changeMobileNum(changeMobileNumberRequest);
        mResponeCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralResponse data = response.body();
                    if (data.isStatus()) {
                        PreferencesManager.getInstance().setUserData(new Gson().toJson(data.getData()));
                        ((ChangePasswordOrMobileActivity) getActivity()).finish();
                        ((ChangePasswordOrMobileActivity) getActivity()).overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    } else {
                        Utility.showAlertMessage(getActivity(), data.getErrorCode());
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
