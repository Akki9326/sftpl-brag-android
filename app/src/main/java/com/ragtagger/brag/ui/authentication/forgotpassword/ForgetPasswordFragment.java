package com.ragtagger.brag.ui.authentication.forgotpassword;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentForgetPassBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.otp.OTPFragment;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.ragtagger.brag.utils.Constants.IPermissionRequestCode.REQ_SMS_SEND_RECEIVED_READ;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class ForgetPasswordFragment extends CoreFragment<FragmentForgetPassBinding, ForgotPasswordViewModel> implements ForgotPasswordNavigator {

    @Inject
    ForgotPasswordViewModel mForgotPasswordViewModel;
    FragmentForgetPassBinding mFragmentForgotPassBinding;

    public static ForgetPasswordFragment newInstance(String mobile) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForgotPasswordViewModel.setNavigator(this);
    }

    /**
     * Get bundle data here
     */
    @Override
    public void beforeViewCreated() {
    }

    @Override
    public void afterViewCreated() {
        mFragmentForgotPassBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentForgotPassBinding.baseLayout);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_MOBILE)) {
            if (getArguments().getString(Constants.BUNDLE_MOBILE).trim().isEmpty()
                    || getArguments().getString(Constants.BUNDLE_MOBILE).length() < 10) {
                mForgotPasswordViewModel.setMobileNumber("");
            } else {
                mForgotPasswordViewModel.setMobileNumber(getArguments().getString(Constants.BUNDLE_MOBILE));
            }
        }
        if (getActivity() instanceof UserProfileActivity) {
            mFragmentForgotPassBinding.edittextMobileNum.setHint(
                    Html.fromHtml("<small><small>" + getString(R.string.hint_new_mobile_num) + "</small></small>"));
            mFragmentForgotPassBinding.textviewMobileNum.setText(getString(R.string.label_new_mobile_no));
        }
    }

    @Override
    public void setUpToolbar() {
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_change_mobile_num));
        }
    }

    @Override
    public ForgotPasswordViewModel getViewModel() {
        return mForgotPasswordViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_pass;
    }

    private boolean checkAndRequestPermissions() {
        boolean hasPermissionSendMessage = hasPermission(Manifest.permission.SEND_SMS);
        boolean hasPermissionReceiveSMS = hasPermission(Manifest.permission.RECEIVE_MMS);
        boolean hasPermissionReadSMS = hasPermission(Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (!hasPermissionSendMessage) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!hasPermissionReceiveSMS) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (!hasPermissionReadSMS) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermission(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQ_SMS_SEND_RECEIVED_READ);
            return false;
        }
        return true;
    }

    @Override
    public void onPermissionGranted(int request) {
        super.onPermissionGranted(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            mForgotPasswordViewModel.callSendOtpApi(mFragmentForgotPassBinding.edittextMobileNum.getText().toString());
        }
    }

    @Override
    public void onPermissionDenied(int request) {
        super.onPermissionDenied(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            mForgotPasswordViewModel.callSendOtpApi(mFragmentForgotPassBinding.edittextMobileNum.getText().toString());
        }
    }

    @Override
    public void performClickSendOtp() {
        if (isAdded())
            mForgotPasswordViewModel.validateOtpForm(getActivity(), mFragmentForgotPassBinding.edittextMobileNum);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validOtpForm() {
        if (checkAndRequestPermissions()) {
            showProgress();
            mForgotPasswordViewModel.callSendOtpApi(mFragmentForgotPassBinding.edittextMobileNum.getText().toString());
        }
    }

    @Override
    public void invalidOtpForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void pushOTPFragmentOnSplashActivity() {
        ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mFragmentForgotPassBinding.edittextMobileNum.getText().toString(),
                Constants.OTPValidationIsFrom.FORGET_PASS.ordinal()),
                true, true, "OTP_frag");

    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }


}
