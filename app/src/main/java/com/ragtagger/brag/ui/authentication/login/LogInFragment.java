package com.ragtagger.brag.ui.authentication.login;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.ragtagger.brag.databinding.FragmentLoginBinding;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.forgotpassword.ForgetPasswordFragment;
import com.ragtagger.brag.ui.contactus.ContactUsFragment;
import com.ragtagger.brag.ui.authentication.signup.SignUpFragment;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class LogInFragment extends CoreFragment<FragmentLoginBinding, LoginViewModel> implements LoginNavigator/*BaseFragment implements BaseInterface*/ {

    @Inject
    LoginViewModel mLoginViewModel;
    FragmentLoginBinding mFragmentLoginBinding;

    public static LogInFragment newInstance() {
        Bundle args = new Bundle();
        LogInFragment fragment = new LogInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentLoginBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentLoginBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void login() {
        if (Validation.isEmpty(mFragmentLoginBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mFragmentLoginBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
        } else if (Validation.isEmptyPassword(mFragmentLoginBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getResources().getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mLoginViewModel.login(mFragmentLoginBinding.edittextMobileNum.getText().toString(), mFragmentLoginBinding.edittextPassword.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }
    }

    @Override
    public void hideUnhidePass() {
        if (mFragmentLoginBinding.imageviewPassVisible.isSelected()) {
            mFragmentLoginBinding.imageviewPassVisible.setSelected(false);
            mFragmentLoginBinding.edittextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mFragmentLoginBinding.edittextPassword.setSelection(mFragmentLoginBinding.edittextPassword.getText().length());
        } else {
            mFragmentLoginBinding.imageviewPassVisible.setSelected(true);
            mFragmentLoginBinding.edittextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mFragmentLoginBinding.edittextPassword.setSelection(mFragmentLoginBinding.edittextPassword.getText().length());
        }
    }

    @Override
    public boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentLoginBinding.textviewLogin.performClick();
            return true;
        }
        return false;
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
    public void openSignUpFragment() {
        ((SplashActivity) getBaseActivity()).pushFragments(new SignUpFragment(), true, true, "Signup_Frag");
    }

    @Override
    public void openContactUsFragment() {
        ((SplashActivity) getBaseActivity()).pushFragments(new ContactUsFragment(), true, true, "Signup_Frag");
    }

    @Override
    public void openForgotPassFragment() {
        ((SplashActivity) getActivity()).pushFragments(ForgetPasswordFragment.newInstance(mFragmentLoginBinding.edittextMobileNum.getText().toString()), true, true, "Forget_Frag");
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(getBaseActivity(), MainActivity.class));
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }


    public String getSDCardDirectory() {
        String SdcardPath = Environment.getExternalStorageDirectory().toString();
        String dir = SdcardPath.substring(SdcardPath.lastIndexOf('/') + 1);
        System.out.println(dir);
        String[] trimmed = SdcardPath.split(dir);
        String sdcardPath = trimmed[0];
        System.out.println(sdcardPath);
        return sdcardPath;
    }
}
