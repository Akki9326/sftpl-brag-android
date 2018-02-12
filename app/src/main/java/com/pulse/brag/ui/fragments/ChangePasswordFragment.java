package com.pulse.brag.ui.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.ui.activities.ChangePasswordOrMobileActivity;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.response.ChangePasswordResponse;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 13-11-2017.
 */


public class ChangePasswordFragment extends BaseFragment implements BaseInterface {

    View mView;

    private EditText mEdtOldPass;
    private TextView mTxtNewPass;
    private EditText mEdtNewPass;
    private EditText mEdtConfirmPass;
    private TextView mTxtDone;

    Context mContext;
    String mobile;

    public static ChangePasswordFragment newInstance(String mobile) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_change_password, container, false);
            initializeData();
            setListeners();
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
        ((ChangePasswordOrMobileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_change_password));
    }

    @Override
    public void initializeData() {
        mEdtOldPass = (EditText) mView.findViewById(R.id.edittext_old_password);
        mTxtNewPass = (TextView) mView.findViewById(R.id.textview_new_pass);
        mEdtNewPass = (EditText) mView.findViewById(R.id.edittext_password);
        mEdtConfirmPass = (EditText) mView.findViewById(R.id.edittext_confirm_password);
        mTxtDone = (TextView) mView.findViewById(R.id.textview_done);
        mobile = getArguments().getString(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void setListeners() {

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
            Utility.showAlertMessage(getActivity(), 0,null);
        }
    }

    private void ChangePasswordAPI() {

        showProgressDialog();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setMobileNumber(mobile);
        changePasswordRequest.setOldPassword(mEdtOldPass.getText().toString());
        changePasswordRequest.setPassword(mEdtNewPass.getText().toString());
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<ChangePasswordResponse> mChangePasswordResponeCall = ApiClient.getInstance(getActivity()).getApiResp().changePassword(changePasswordRequest);
        mChangePasswordResponeCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    ChangePasswordResponse respone = response.body();
                    if (respone.isStatus()) {
                        Utility.showAlertMessage(getContext(), getString(R.string.msg_password_change_successfull), true);
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode(),respone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1,null);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }

    @Override
    public void showData() {

    }


}
