package com.pulse.brag.ui.authentication.signup.complete;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentSignupComplateBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class SignUpCompleteFragment extends CoreFragment<FragmentSignupComplateBinding, SignUpCompleteViewModel> implements SignUpCompleteNavigator/*extends BaseFragmentimplements BaseInterface*/ {

    @Inject
    SignUpCompleteViewModel mSignUpCompleteViewModel;

    FragmentSignupComplateBinding mFragmentSignupComplateBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpCompleteViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentSignupComplateBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentSignupComplateBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public SignUpCompleteViewModel getViewModel() {
        return mSignUpCompleteViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_signup_complate;
    }

    @Override
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

    }

    @Override
    public void backToLogin() {
        ((SplashActivity) getActivity()).popBackToLogin();
    }
}
