package com.ragtagger.brag.ui.authentication.signup;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.databinding.FragmentSignUpBinding;
import com.ragtagger.brag.data.model.requests.QSignUp;
import com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogFragment;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.otp.OTPFragment;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.TextFilterUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;
import com.ragtagger.brag.views.dropdown.DropdownItem;
import com.ragtagger.brag.views.dropdown.DropdownUtils;
import com.ragtagger.brag.views.dropdown.IOnDropDownItemClick;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.ragtagger.brag.utils.Constants.IPermissionRequestCode.REQ_SMS_SEND_RECEIVED_READ;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class SignUpFragment extends CoreFragment<FragmentSignUpBinding, SignUpViewModel> implements SignUpNavigator {

    public static final int REQUEST_STATE = 1;

    @Inject
    SignUpViewModel mSignUpViewModel;
    FragmentSignUpBinding mFragmentSignUpBinding;

    ArrayList<DropdownItem> mUserTypeList;
    ArrayList<DropdownItem> mChannelList;
    ArrayList<DropdownItem> mSaleTypeList;
    List<DataState> mStateList;

    int mSelectedUserType, mSelectedChannel, mSelectedSaleType, mSelectedStateId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mSignUpViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {
        mUserTypeList = new ArrayList<>();
        mStateList = new ArrayList<>();
        mChannelList = new ArrayList<>();
        mSaleTypeList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentSignUpBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentSignUpBinding.baseLayout);

        mFragmentSignUpBinding.edittextGstIn.setFilters(new InputFilter[]{TextFilterUtils.getAlphaNumericFilter(), TextFilterUtils.getLengthFilter(15)});
        mUserTypeList.add(new DropdownItem("Retailer", 0));
        mUserTypeList.add(new DropdownItem("Distributor", 1));
        mUserTypeList.add(new DropdownItem("Sales representative", 2));
        mFragmentSignUpBinding.textviewUserType.setText(mUserTypeList.get(0).getValue());
        mSelectedUserType = mUserTypeList.get(0).getId();


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

    private void checkInternetAndCallApi() {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_STATE) {
            /*selectedState = data.getParcelableExtra(Constants.BUNDLE_KEY_STATE);
            mAddEditViewModel.updateState(selectedState.getText());
            stateId = selectedState.getId();*/
        }
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

    @Override
    public boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentSignUpBinding.textviewSignup.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void performClickUserTypeDropdown(View view) {
        try {
            DropdownUtils.DropDownSpinner(getActivity(), mUserTypeList, view, new IOnDropDownItemClick() {
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
    public void performClickChannelDropdown(View view) {

    }

    @Override
    public void performClickSaleTypeDropdown(View view) {

    }

    @Override
    public void performClickState(View view) {
        if (mStateList.isEmpty()) {
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.BUNDLE_KEY_STATE_LIST, (ArrayList<? extends Parcelable>) mStateList);
            StateDialogFragment dialogFragment = new StateDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.setTargetFragment(this, REQUEST_STATE);
            dialogFragment.show(getFragmentManager(), "");
        }
    }

    @Override
    public void performClickSignUp() {
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
        } else if (Validation.isEmptyPassword(mFragmentSignUpBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Validation.isEmptyPassword(mFragmentSignUpBinding.edittextConfirmPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
        } else if (!(mFragmentSignUpBinding.edittextPassword.getText().toString().equals(mFragmentSignUpBinding.edittextConfirmPassword.getText().toString()))) {
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
    public void noInternet() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validSignUpForm() {
        Utility.hideSoftkeyboard(getActivity());
        if (checkAndRequestPermissions()) {
            showProgress();
            mSignUpViewModel.callSignUpApi(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString());
        }
    }

    @Override
    public void invalidSignUpForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void pushOtpFragment() {
        ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                , Constants.OTPValidationIsFrom.SIGN_UP.ordinal()),
                true, true, "OTP_Frag");
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
