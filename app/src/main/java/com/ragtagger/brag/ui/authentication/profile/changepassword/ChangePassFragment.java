package com.ragtagger.brag.ui.authentication.profile.changepassword;


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
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentChangePasswordBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import javax.inject.Inject;
import javax.xml.validation.Validator;

/**
 * Created by nikhil.vadoliya on 13-11-2017.
 */


public class ChangePassFragment extends CoreFragment<FragmentChangePasswordBinding, ChangePassViewModel> implements ChangePassNavigator {

    @Inject
    ChangePassViewModel mChangePassViewModel;
    FragmentChangePasswordBinding mChangePasswordBinding;

    String mobile;

    public static ChangePassFragment newInstance(String mobile) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        ChangePassFragment fragment = new ChangePassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangePassViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mobile = getArguments().getString(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void afterViewCreated() {
        mChangePasswordBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mChangePasswordBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {
        ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_change_password));
    }

    @Override
    public ChangePassViewModel getViewModel() {
        return mChangePassViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mChangePasswordBinding.textviewDone.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void performClickDone() {
        if (isAdded())
            mChangePassViewModel.validateChangePasswordForm(getActivity(), mChangePasswordBinding.edittextOldPassword, mChangePasswordBinding.edittextPassword, mChangePasswordBinding.edittextConfirmPassword);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validChangePasswordForm() {
        showProgress();
        mChangePassViewModel.changePassword(mobile, mChangePasswordBinding.edittextOldPassword.getText().toString(), mChangePasswordBinding.edittextPassword.getText().toString());
    }

    @Override
    public void invalidChangePasswordForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void showMsgPasswordChange() {
        AlertUtils.showAlertMessage(getBaseActivity(), getString(R.string.msg_password_change_successfull), true);
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
