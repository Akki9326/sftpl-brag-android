package com.ragtagger.brag.ui.authentication.profile.changemobile;


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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentChangeMobileNumBinding;
import com.ragtagger.brag.ui.authentication.otp.OTPFragment;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.ragtagger.brag.utils.Constants.IPermissionRequestCode.REQ_SMS_SEND_RECEIVED_READ;

/**
 * Created by nikhil.vadoliya on 11-12-2017.
 */


public class ChangeMobileNumberFragment extends CoreFragment<FragmentChangeMobileNumBinding, ChangeMobNumberViewModel> implements ChangeMobNumberNavigator/*extends BaseFragment implements BaseInterface*/ {

    @Inject
    ChangeMobNumberViewModel mChangeMobNumberViewModel;
    FragmentChangeMobileNumBinding mFragmentChangeMobileNumBinding;


    public static ChangeMobileNumberFragment newInstance() {
        ChangeMobileNumberFragment fragment = new ChangeMobileNumberFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangeMobNumberViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
    }

    @Override
    public void afterViewCreated() {
        mFragmentChangeMobileNumBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentChangeMobileNumBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {
        ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.label_title_change_mob_no));
    }

    @Override
    public ChangeMobNumberViewModel getViewModel() {
        return mChangeMobNumberViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_mobile_num;
    }

    @Override
    public void onPermissionGranted(int request) {
        super.onPermissionGranted(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            mChangeMobNumberViewModel.sendOtpForChangeMob(mFragmentChangeMobileNumBinding.edittextMobileNum.getText().toString()
                    , mFragmentChangeMobileNumBinding.edittextPassword.getText().toString());
        }
    }

    @Override
    public void onPermissionDenied(int request) {
        super.onPermissionDenied(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ) {
            showProgress();
            mChangeMobNumberViewModel.sendOtpForChangeMob(mFragmentChangeMobileNumBinding.edittextMobileNum.getText().toString()
                    , mFragmentChangeMobileNumBinding.edittextPassword.getText().toString());
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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
    }

    @Override
    public void done() {
        if (Validation.isEmpty(mFragmentChangeMobileNumBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mFragmentChangeMobileNumBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
        } else if (Validation.isEmpty(mFragmentChangeMobileNumBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            if (checkAndRequestPermissions()) {
                showProgress();
                mChangeMobNumberViewModel.sendOtpForChangeMob(mFragmentChangeMobileNumBinding.edittextMobileNum.getText().toString()
                        , mFragmentChangeMobileNumBinding.edittextPassword.getText().toString());
            }
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }
    }

    @Override
    public void hideUnhidePass() {
        if (mFragmentChangeMobileNumBinding.imageviewPassVisible.isSelected()) {
            mFragmentChangeMobileNumBinding.imageviewPassVisible.setSelected(false);
            mFragmentChangeMobileNumBinding.edittextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mFragmentChangeMobileNumBinding.edittextPassword.setSelection(mFragmentChangeMobileNumBinding.edittextPassword.getText().length());
        } else {
            mFragmentChangeMobileNumBinding.imageviewPassVisible.setSelected(true);
            mFragmentChangeMobileNumBinding.edittextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mFragmentChangeMobileNumBinding.edittextPassword.setSelection(mFragmentChangeMobileNumBinding.edittextPassword.getText().length());
        }
    }

    @Override
    public boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentChangeMobileNumBinding.textviewDone.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void pushOTPFragment() {
        ((UserProfileActivity) getActivity()).pushFragmentInChangeContainer(OTPFragment.newInstanceForChangeMobile(mFragmentChangeMobileNumBinding.edittextMobileNum.getText().toString(),
                mFragmentChangeMobileNumBinding.edittextPassword.getText().toString(), Constants.OTPValidationIsFrom.CHANGE_MOBILE.ordinal()),
                true, true, "OTP_frag");
    }

    @Override
    public void finishActivity() {
        ((UserProfileActivity) getActivity()).finish();
        ((UserProfileActivity) getActivity()).overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


}
