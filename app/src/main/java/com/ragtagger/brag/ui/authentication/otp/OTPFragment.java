package com.ragtagger.brag.ui.authentication.otp;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentOtpBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.createnewpassord.CreateNewPasswordFragment;
import com.ragtagger.brag.ui.authentication.otp.autoread.SmsListener;
import com.ragtagger.brag.ui.authentication.otp.autoread.SmsReceiver;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.ui.authentication.signup.complete.SignUpCompleteFragment;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

import static com.ragtagger.brag.utils.Constants.OTPValidationIsFrom.CHANGE_MOBILE;
import static com.ragtagger.brag.utils.Constants.OTPValidationIsFrom.FORGET_PASS;

/**
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class OTPFragment extends CoreFragment<FragmentOtpBinding, OTPViewModel> implements OTPNavigator/*BaseFragment implements BaseInterface*/ {

    @Inject
    OTPViewModel mOTPViewModel;
    FragmentOtpBinding mFragmentOtpBinding;

    IntentFilter intentFilter;

    String mobileNum;
    String password;
    int isFromScreen;//is from signup or forget pass

    public static OTPFragment newInstance(String mobilenum, int isFromScreen) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobilenum);
        args.putInt(Constants.BUNDLE_IS_FROM_SIGNUP, isFromScreen);
        OTPFragment fragment = new OTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OTPFragment newInstanceForChangeMobile(String newMobileNumber, String password, int isFromScreen) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, newMobileNumber);
        args.putInt(Constants.BUNDLE_IS_FROM_SIGNUP, isFromScreen);
        args.putString(Constants.BUNDLE_PASSWORD, password);
        OTPFragment fragment = new OTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOTPViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {
        mobileNum = getArguments().getString(Constants.BUNDLE_MOBILE);
        isFromScreen = getArguments().getInt(Constants.BUNDLE_IS_FROM_SIGNUP);

        if (Constants.OTPValidationIsFrom.values()[isFromScreen] == CHANGE_MOBILE)
            password = getArguments().getString(Constants.BUNDLE_PASSWORD);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        getActivity().registerReceiver(new SmsReceiver(), intentFilter);


    }

    @Override
    public void afterViewCreated() {
        mFragmentOtpBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mFragmentOtpBinding.linearLayout);
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                mFragmentOtpBinding.pinView.setText(messageText);
                AppLogger.e("Received Msg : " + messageText);
            }
        });
    }

    @Override
    public void setUpToolbar() {
        if (Constants.OTPValidationIsFrom.values()[isFromScreen] == CHANGE_MOBILE)
            ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.label_title_verify_otp));
    }

    @Override
    public OTPViewModel getViewModel() {
        return mOTPViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_otp;
    }

    @Override
    public boolean onEditorActionPin(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentOtpBinding.textviewVerify.performClick();
            return true;
        }
        return false;
    }


    @Override
    public void performClickVerifyOtp() {
        if (isAdded())
            mOTPViewModel.validateOtpForm(getActivity(), mFragmentOtpBinding.pinView);
    }

    @Override
    public void performClickResendOtp() {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            mOTPViewModel.callResendOtpApi(mobileNum, isFromScreen, password);
        } else {
            noInternetAlert();
        }
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validOtpForm() {
        showProgress();
        if (Constants.OTPValidationIsFrom.values()[isFromScreen] == CHANGE_MOBILE)
            mOTPViewModel.callVerifyOtpForChangeMobApi(mobileNum, password, mFragmentOtpBinding.pinView.getText().toString());
        else if ((Constants.OTPValidationIsFrom.values()[isFromScreen] == FORGET_PASS))
            mOTPViewModel.callVerifyOtpForForgotPassApi(mobileNum, mFragmentOtpBinding.pinView.getText().toString(), isFromScreen);
        else
            mOTPViewModel.callVerifyOtpApi(mobileNum, mFragmentOtpBinding.pinView.getText().toString(), isFromScreen);
    }

    @Override
    public void inValidOtpForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void finishUserProfileActivity() {
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_PROFILE);
        intent.putExtra(Constants.BUNDLE_IS_ADDRESS_UPDATE, false);
        intent.putExtra(Constants.BUNDLE_KEY_MOBILE_NUM, mobileNum);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        ((UserProfileActivity) getActivity()).finish();
        ((UserProfileActivity) getActivity()).overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public void pushCreatePasswordFragment() {
        ((SplashActivity) getActivity()).pushFragments(CreateNewPasswordFragment.newInstance(mobileNum, mFragmentOtpBinding.pinView.getText().toString()),
                true, true, "Create_Pass_Frag");
    }

    @Override
    public void pushSignUpCompleteFragment() {
        ((SplashActivity) getActivity()).pushFragments(new SignUpCompleteFragment(),
                true, true, "Signup_Complete_Frag");
    }

    @Override
    public void onOtpVerifySuccessfully() {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), getString(R.string.msg_otp_resend));
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
