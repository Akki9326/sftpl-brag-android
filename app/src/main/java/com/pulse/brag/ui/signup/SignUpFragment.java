package com.pulse.brag.ui.signup;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentSignUpBinding;
import com.pulse.brag.pojo.requests.SignInRequest;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.otp.OTPFragment;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class SignUpFragment extends CoreFragment<FragmentSignUpBinding, SignUpViewModel> implements SignUpNavigator/*extends BaseFragment implements BaseInterface*/ {

    @Inject
    SignUpViewModel mSignUpViewModel;
    FragmentSignUpBinding mFragmentSignUpBinding;




    /*
    View mView;
    EditText mEdtFirstNam, mEdtLastNam, mEdtEmail, mEdtMobile, mEdtPass, mEdtConfirmPas;
    Button mBtnSignup, mBtnLogin;
    TextView mTxtSignup;
    View mDummyView;*/

    int keyboardheight;

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mSignUpViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentSignUpBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentSignUpBinding.baseLayout);
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



   /* @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
        mTxtSignup = (TextView) mView.findViewById(R.id.textview_signup);
        mEdtFirstNam = (EditText) mView.findViewById(R.id.edittext_firstname);
        mEdtLastNam = (EditText) mView.findViewById(R.id.edittext_lastname);
        mEdtEmail = (EditText) mView.findViewById(R.id.edittext_email);
        mEdtMobile = (EditText) mView.findViewById(R.id.edittext_mobile_num);
        mEdtPass = (EditText) mView.findViewById(R.id.edittext_password);
        mEdtConfirmPas = (EditText) mView.findViewById(R.id.edittext_confirm_password);
        mDummyView = mView.findViewById(R.id.view_dummy);

    }

    @Override
    public void setListeners() {
        mTxtSignup.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {


                if (Validation.isEmpty(mEdtFirstNam)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_please_enter_first_name));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_enter_first_name));
                } else if (Validation.isEmpty(mEdtEmail)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_please_email));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_email));
                } else if (!Validation.isEmailValid(mEdtEmail)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_email_valid));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_email_valid));
                } else if (Validation.isEmpty(mEdtMobile)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (!Validation.isValidMobileNum(mEdtMobile)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Validation.isEmpty(mEdtPass)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_pass));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
                } else if (Validation.isEmpty(mEdtConfirmPas)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
                } else if (!(mEdtPass.getText().toString().trim().equals(mEdtConfirmPas.getText().toString().trim()))) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_password_not_match));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_password_not_match));
                } else if (Utility.isConnection(getActivity())) {
                    SignInRequest signInRequest = new SignInRequest("Mr", mEdtFirstNam.getText().toString(), mEdtLastNam.getText().toString().trim(),
                            mEdtEmail.getText().toString(), mEdtMobile.getText().toString()
                            , mEdtPass.getText().toString());
                    SignInAPICall(signInRequest);
                } else {
                    //Utility.showAlertMessage(getActivity(), 0, null);
                    AlertUtils.showAlertMessage(getActivity(), 0, null);
                }
            }
        });

        mEdtConfirmPas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtSignup.performClick();
                    return true;
                }
                return false;
            }
        });

        *//*KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            mDummyView.setMinimumHeight(keyboardheight);
                            mDummyView.setVisibility(View.VISIBLE);
                        } else {
                            mDummyView.setVisibility(View.GONE);
                        }
                    }
                });


        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mView.getWindowVisibleDisplayFrame(r);

                int screenHeight = mView.getRootView().getHeight();
                int heightDifference = screenHeight - (r.bottom - r.top);
                keyboardheight = heightDifference;
                Log.d("Keyboard Size", "Size: " + heightDifference);

                //boolean visible = heightDiff > screenHeight / 3;
            }
        });*//*
    }

    @Override
    public void showData() {

    }*/


    /*private void SignInAPICall(final SignInRequest signInRequest) {
        Utility.hideSoftkeyboard(getActivity());
        showProgressDialog();
        Call<SignUpResponse> mSignUpResponeCall = ApiClient.getInstance(getActivity()).getApiResp().userSignIn(signInRequest);
        mSignUpResponeCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    SignUpResponse signUpRespone = response.body();
                    if (signUpRespone.isStatus()) {

                        ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mEdtMobile.getText().toString()
                                , mEdtEmail.getText().toString(), OTPValidationIsFrom.SIGN_UP.ordinal()),
                                true, true, "OTP_Frag");


                    } else {
                        //Utility.showAlertMessage(getActivity(), signUpRespone.getErrorCode(), signUpRespone.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), signUpRespone.getErrorCode(), signUpRespone.getMessage());
                    }
                } else {
                    //Utility.showAlertMessage(getActivity(), 1, null);
                    AlertUtils.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getActivity(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });

    }*/


    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        showProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
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
        } else if (Utility.isConnection(getActivity())) {
            Utility.hideSoftkeyboard(getActivity());
            // TODO: 2/15/2018 change title in api call it is backend issue
            SignInRequest signInRequest = new SignInRequest("Mr", mFragmentSignUpBinding.edittextFirstname.getText().toString(), mFragmentSignUpBinding.edittextLastname.getText().toString().trim(),
                    mFragmentSignUpBinding.edittextEmail.getText().toString(), mFragmentSignUpBinding.edittextMobileNum.getText().toString()
                    , mFragmentSignUpBinding.edittextPassword.getText().toString());
            mSignUpViewModel.signUp(signInRequest);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
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
                , mFragmentSignUpBinding.edittextEmail.getText().toString(), Constants.OTPValidationIsFrom.SIGN_UP.ordinal()),
                true, true, "OTP_Frag");
    }
}
