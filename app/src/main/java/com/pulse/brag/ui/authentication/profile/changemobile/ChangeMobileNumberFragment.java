package com.pulse.brag.ui.authentication.profile.changemobile;


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
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentChangeMobileNumBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 11-12-2017.
 */


public class ChangeMobileNumberFragment extends CoreFragment<FragmentChangeMobileNumBinding, ChangeMobNumberViewModel> implements ChangeMobNumberNavigator/*extends BaseFragment implements BaseInterface*/ {

    @Inject
    ChangeMobNumberViewModel mChangeMobNumberViewModel;
    FragmentChangeMobileNumBinding mFragmentChangeMobileNumBinding;

    String mobilenum;

    public static ChangeMobileNumberFragment newInstance(String mobilenum) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobilenum);
        ChangeMobileNumberFragment fragment = new ChangeMobileNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangeMobNumberViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mobilenum = getArguments().getString(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void afterViewCreated() {
        mFragmentChangeMobileNumBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentChangeMobileNumBinding.baseLayout);
        mChangeMobNumberViewModel.setNewMobileNumber(mobilenum);
    }

    @Override
    public void setUpToolbar() {
        ((UserProfileActivity) getActivity()).showToolBar("Change Mobile Number");
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
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void done() {
        if (Validation.isEmpty(mFragmentChangeMobileNumBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mChangeMobNumberViewModel.changeMobNumber(mFragmentChangeMobileNumBinding.textviewNewMobileNum.getText().toString()
                    , mFragmentChangeMobileNumBinding.edittextPassword.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
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
    public void finishActivity() {
        ((UserProfileActivity) getActivity()).finish();
        ((UserProfileActivity) getActivity()).overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
