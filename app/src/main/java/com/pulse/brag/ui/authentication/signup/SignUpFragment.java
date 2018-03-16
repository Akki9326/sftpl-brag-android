package com.pulse.brag.ui.authentication.signup;

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
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentSignUpBinding;
import com.pulse.brag.data.model.requests.QSignUp;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.authentication.otp.OTPFragment;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.AppLogger;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.TextFilterUtils;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;
import com.pulse.brag.views.dropdown.DropdownItem;
import com.pulse.brag.views.dropdown.DropdownUtils;
import com.pulse.brag.views.dropdown.IOnDropDownItemClick;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.pulse.brag.utils.Constants.IPermissionRequestCode.REQ_SMS_SEND_RECEIVED_READ;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class SignUpFragment extends CoreFragment<FragmentSignUpBinding, SignUpViewModel> implements SignUpNavigator/*extends BaseFragment implements BaseInterface*/ {

    @Inject
    SignUpViewModel mSignUpViewModel;
    FragmentSignUpBinding mFragmentSignUpBinding;

    ArrayList<DropdownItem> userTypeList;
    int mSelectedUserType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mSignUpViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {
        userTypeList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentSignUpBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentSignUpBinding.baseLayout);

        mFragmentSignUpBinding.edittextGstIn.setFilters(new InputFilter[]{TextFilterUtils.getAlphaNumericFilter(), TextFilterUtils.getLengthFilter(15)});
        userTypeList.add(new DropdownItem("Retailer", 0));
        userTypeList.add(new DropdownItem("Distributor", 1));
        mFragmentSignUpBinding.textviewUserType.setText(userTypeList.get(0).getValue());
        mSelectedUserType = userTypeList.get(0).getId();
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public SignUpViewModel getViewModel() {
        return mSignUpViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onPermissionDenied(int request) {
        super.onPermissionDenied(request);

        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            QSignUp signInRequest = new QSignUp(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString());
            mSignUpViewModel.signUp(signInRequest);
        }
    }

    @Override
    public void onPermissionGranted(int request) {
        super.onPermissionGranted(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            QSignUp signInRequest = new QSignUp(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString());
            mSignUpViewModel.signUp(signInRequest);
        }
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
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void typeDropdown(View view) {
        try {
            DropdownUtils.DropDownSpinner(getActivity(), userTypeList, view, new IOnDropDownItemClick() {
                @Override
                public void onItemClick(String str, int i) {
                    mSelectedUserType = i;
                }
            });
        } catch (Exception e) {
            AppLogger.e(e.getMessage());
        }

    }

    @Override
    public void signUp() {
        if (Validation.isEmpty(mFragmentSignUpBinding.edittextFirstname)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_enter_first_name));
        } else if (Validation.isEmpty(mFragmentSignUpBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(mFragmentSignUpBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(mFragmentSignUpBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mFragmentSignUpBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
        } else if (Validation.isEmpty(mFragmentSignUpBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Validation.isEmpty(mFragmentSignUpBinding.edittextConfirmPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
        } else if (!(mFragmentSignUpBinding.edittextPassword.getText().toString().trim().equals(mFragmentSignUpBinding.edittextConfirmPassword.getText().toString().trim()))) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_password_not_match));
        } else if (Validation.isEmpty(mFragmentSignUpBinding.edittextGstIn)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_gst));
        } else if (!Validation.isValidGST(mFragmentSignUpBinding.edittextGstIn)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_gst_valid));
        } else if (Utility.isConnection(getActivity())) {
            Utility.hideSoftkeyboard(getActivity());
            if (checkAndRequestPermissions()) {
                showProgress();
                QSignUp signInRequest = new QSignUp(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                        mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                        , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString());
                mSignUpViewModel.signUp(signInRequest);
            }
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentSignUpBinding.textviewSignup.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void pushOtpFragment() {
        ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                , Constants.OTPValidationIsFrom.SIGN_UP.ordinal()),
                true, true, "OTP_Frag");
    }
}
