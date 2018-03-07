package com.pulse.brag.ui.authentication.otp;


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
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentOtpBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.authentication.createnewpassord.CreateNewPasswordFragment;
import com.pulse.brag.ui.authentication.otp.autoread.SmsListener;
import com.pulse.brag.ui.authentication.otp.autoread.SmsReceiver;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.ui.authentication.profile.changemobile.ChangeMobileNumberFragment;
import com.pulse.brag.ui.authentication.signup.complete.SignUpCompleteFragment;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.AppLogger;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class OTPFragment extends CoreFragment<FragmentOtpBinding, OTPViewModel> implements OTPNavigator/*BaseFragment implements BaseInterface*/ {

    @Inject
    OTPViewModel mOTPViewModel;
    FragmentOtpBinding mFragmentOtpBinding;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOTPViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {
        mobileNum = getArguments().getString(Constants.BUNDLE_MOBILE);
        emailAddress = getArguments().getString(Constants.BUNDLE_EMAIL);
        isFromScreen = getArguments().getInt(Constants.BUNDLE_IS_FROM_SIGNUP);
    }

    @Override
    public void afterViewCreated() {
        mFragmentOtpBinding = getViewDataBinding();
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
                    ((UserProfileActivity) getActivity()).finish();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void verifyOtp() {
        if (mFragmentOtpBinding.pinView.getText().toString().length() < 6) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_otp));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mOTPViewModel.verifyOtp(mobileNum, mFragmentOtpBinding.pinView.getText().toString(), isFromScreen);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void resendOtp() {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            mOTPViewModel.resendOtp(mobileNum);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }

    }

    @Override
    public boolean onEditorActionPin(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentOtpBinding.textviewVerify.performClick();
            return true;
        }
        return false;
    }



    /*@Override
    public void pushChangeMobileFragment() {
        ((UserProfileActivity) getActivity()).pushFragmentInChangeContainer(ChangeMobileNumberFragment.newInstance(mobileNum)
                , true, true, "");
    }*/

    @Override
    public void finishUserProfileActivity() {
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
}
