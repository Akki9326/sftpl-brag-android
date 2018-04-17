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
import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.databinding.FragmentSignUpBinding;
import com.ragtagger.brag.ui.authentication.otp.OTPFragment;
import com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogFragment;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.TextFilterUtils;
import com.ragtagger.brag.utils.Utility;
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

    ArrayList<DropdownItem> mUserTypeDropDown;
    ArrayList<DropdownItem> mChannelDropDown;
    ArrayList<DropdownItem> mSaleTypeDropDown;

    List<DataState> mStateList;
    List<DataChannel> mChannelList;
    List<DataSaleType> mSalesTypeList;

    int mSelectedUserType;
    DataChannel mSelectedChannel;
    DataSaleType mSelectedSalesType;
    DataState mSelectedState;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mSignUpViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {
        mUserTypeDropDown = new ArrayList<>();
        mChannelDropDown = new ArrayList<>();
        mSaleTypeDropDown = new ArrayList<>();

        mStateList = new ArrayList<>();
        mChannelList = new ArrayList<>();
        mSalesTypeList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentSignUpBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentSignUpBinding.baseLayout);

        mFragmentSignUpBinding.edittextGstIn.setFilters(new InputFilter[]{TextFilterUtils.getAlphaNumericFilter(), TextFilterUtils.getLengthFilter(15)});
        mUserTypeDropDown.add(new DropdownItem("Retailer", Constants.UserType.RETAILER.getId()));
        mUserTypeDropDown.add(new DropdownItem("Distributor", Constants.UserType.DISTRIBUTOR.getId()));
        mUserTypeDropDown.add(new DropdownItem("Sales representative", Constants.UserType.SALES_REPRESENTATIVE.getId()));
        mFragmentSignUpBinding.textviewUserType.setText(mUserTypeDropDown.get(0).getValue());
        mSelectedUserType = mUserTypeDropDown.get(0).getId();

        checkInternetAndCallApi();
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
        if (isAdded()) {
            if (Utility.isConnection(getActivity())) {
                showProgress();
                mSignUpViewModel.callGetRequiredDataApi();
            } else {
                noInternetAlert();
            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_STATE) {
            mSelectedState = data.getParcelableExtra(Constants.BUNDLE_KEY_STATE);
            mFragmentSignUpBinding.edittextState.setText(mSelectedState.getText());
        }
    }

    @Override
    public void onPermissionDenied(int request) {
        super.onPermissionDenied(request);

        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            mSignUpViewModel.callSignUpApi(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString(), mSelectedState, mSelectedSalesType, mSelectedChannel);
        }
    }

    @Override
    public void onPermissionGranted(int request) {
        super.onPermissionGranted(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            mSignUpViewModel.callSignUpApi(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString(), mSelectedState, mSelectedSalesType, mSelectedChannel);
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
    public void setState(List<DataState> states) {
        mStateList = states;
    }

    @Override
    public void setChannel(List<DataChannel> channels) {
        int i = 0;
        if (channels != null && channels.size() > 0) {
            mChannelList = channels;
            for (DataChannel channel : channels) {
                mChannelDropDown.add(new DropdownItem(channel.getText(), i));
                i++;
            }
            mFragmentSignUpBinding.textviewChannel.setText(mChannelDropDown.get(0).getValue());
            mSelectedChannel = mChannelList.get(0);
        }

    }

    @Override
    public void setSalesType(List<DataSaleType> salesTypes) {
        int i = 0;
        if (salesTypes != null && salesTypes.size() > 0) {
            mSalesTypeList = salesTypes;
            for (DataSaleType salesType : salesTypes) {
                mSaleTypeDropDown.add(new DropdownItem(salesType.getText(), i));
                i++;
            }
            mFragmentSignUpBinding.textviewSaleType.setText(mSaleTypeDropDown.get(0).getValue());
            mSelectedSalesType = mSalesTypeList.get(0);
        }
    }

    @Override
    public void performClickUserTypeDropdown(View view) {
        try {
            DropdownUtils.DropDownSpinner(getActivity(), mUserTypeDropDown, view, new IOnDropDownItemClick() {
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
        if (mChannelList == null || mChannelList.isEmpty()) {

        } else {
            try {
                DropdownUtils.DropDownSpinner(getActivity(), mChannelDropDown, view, new IOnDropDownItemClick() {
                    @Override
                    public void onItemClick(String str, int i) {
                        mSelectedChannel = mChannelList.get(i);
                    }
                });
            } catch (Exception e) {
                AppLogger.e(e.getMessage());
            }
        }
    }

    @Override
    public void performClickSaleTypeDropdown(View view) {
        if (mSalesTypeList == null || mSalesTypeList.isEmpty()) {

        } else {
            try {
                DropdownUtils.DropDownSpinner(getActivity(), mSaleTypeDropDown, view, new IOnDropDownItemClick() {
                    @Override
                    public void onItemClick(String str, int i) {
                        mSelectedSalesType = mSalesTypeList.get(i);
                    }
                });
            } catch (Exception e) {
                AppLogger.e(e.getMessage());
            }
        }
    }

    @Override
    public void performClickState(View view) {
        if (mStateList == null || mStateList.isEmpty()) {
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
        if (isAdded())
            mSignUpViewModel.validateSignUpForm(getActivity(), mFragmentSignUpBinding.edittextFirstname, mFragmentSignUpBinding.edittextEmail, mFragmentSignUpBinding.edittextMobileNum, mFragmentSignUpBinding.edittextPassword, mFragmentSignUpBinding.edittextConfirmPassword, mFragmentSignUpBinding.edittextGstIn, mFragmentSignUpBinding.edittextState);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validSignUpForm() {
        Utility.hideSoftkeyboard(getActivity());
        if (checkAndRequestPermissions()) {
            showProgress();
            mSignUpViewModel.callSignUpApi(mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString(), mSelectedUserType, mFragmentSignUpBinding.edittextGstIn.getText().toString(), mSelectedState, mSelectedSalesType, mSelectedChannel);
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
