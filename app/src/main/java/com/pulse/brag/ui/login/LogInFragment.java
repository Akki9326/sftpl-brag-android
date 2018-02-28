package com.pulse.brag.ui.login;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
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
import com.pulse.brag.databinding.FragmentLoginBinding;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.R;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.forgotpassword.ForgetPasswordFragment;
import com.pulse.brag.ui.contactus.ContactUsFragment;
import com.pulse.brag.ui.signup.SignUpFragment;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class LogInFragment extends CoreFragment<FragmentLoginBinding, LoginViewModel> implements LoginNavigator/*BaseFragment implements BaseInterface*/ {

    @Inject
    LoginViewModel mLoginViewModel;
    FragmentLoginBinding mFragmentLoginBinding;

   /* View mView;
    TextView mTxtLogin, mTxtSignUp, mTxtForget, mTxtContactUs;
    ImageView mImgPass;*/
    //EditText mEdtNumber, mEdtPassword;

    public static LogInFragment newInstance() {
        Bundle args = new Bundle();
        LogInFragment fragment = new LogInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_login, container, false);
            //initializeData();
            //setListeners();
            //showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }*/

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



   /* @Override
    public void setToolbar() {
    }

    @Override
    public void initializeData() {
        mTxtLogin = mView.findViewById(R.id.textview_login);
        mTxtSignUp = mView.findViewById(R.id.textview_signup);
        mImgPass = mView.findViewById(R.id.imageview_pass_visible);
        mEdtPassword = mView.findViewById(R.id.edittext_password);
        mEdtNumber = mView.findViewById(R.id.edittext_mobile_num);
        mTxtForget = mView.findViewById(R.id.textview_forget);
        mTxtContactUs = mView.findViewById(R.id.textview_contact);
        //mEdtNumber.setText("7874487853");
        //mEdtPassword.setText("sailfin*123");
    }

    @Override
    public void setListeners() {

        mTxtLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (Validation.isEmpty(mEdtNumber)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (!Validation.isValidMobileNum(mEdtNumber)) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Validation.isEmpty(mEdtPassword)) {
                    Utility.showAlertMessage(getActivity(), getResources().getString(R.string.error_pass));
                } else if (Utility.isConnection(getActivity())) {
                    LoginAPICall(mEdtNumber.getText().toString(), mEdtPassword.getText().toString());
                } else {
                    Utility.showAlertMessage(getActivity(), 0, null);
                }
            }
        });

        mTxtSignUp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ((SplashActivity) getActivity()).pushFragments(new SignUpFragment(), true, true, "Signup_Frag");
            }
        });
        mTxtContactUs.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ((SplashActivity) getActivity()).pushFragments(new ContactUsFragment(), true, true, "Signup_Frag");

            }
        });

        mEdtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtLogin.performClick();
                    return true;
                }
                return false;
            }
        });

        mImgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImgPass.isSelected()) {
                    mImgPass.setSelected(false);
                    mEdtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEdtPassword.setSelection(mEdtPassword.getText().length());
                } else {
                    mImgPass.setSelected(true);
                    mEdtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEdtPassword.setSelection(mEdtPassword.getText().length());
                }
            }
        });
        mTxtForget.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                ((SplashActivity) getActivity()).pushFragments(ForgetPasswordFragment.newInstance(mEdtNumber.getText().toString()), true, true, "Forget_Frag");

            }
        });
    }

    @Override
    public void showData() {
        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
    }**/


   /* private void LoginAPICall(String mobile, String password) {

        showProgressDialog();
        LoginRequest loginRequest = new LoginRequest(mobile, password);
        Call<LoginResponse> mLoginResponeCall = ApiClient.getInstance(getActivity()).getApiResp().userLogin(loginRequest);
        mLoginResponeCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    LoginResponse respone = response.body();
                    okhttp3.Headers headers = response.headers();
                    if (respone.isStatus()) {

                        setHeaderInPref(headers);
                        PreferencesManager.getInstance().setIsLogin(true);
                        PreferencesManager.getInstance().setUserData(new Gson().toJson(respone.getData()));
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }


    private void setHeaderInPref(okhttp3.Headers headers) {
        PreferencesManager.getInstance().setAccessToken(headers.get("access_token"));
        PreferencesManager.getInstance().setDeviceToken(FirebaseInstanceId.getInstance().getToken());

    }*/

    @Override
    public void login() {
        if (Validation.isEmpty(mFragmentLoginBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mFragmentLoginBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
        } else if (Validation.isEmpty(mFragmentLoginBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getResources().getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mLoginViewModel.login(mFragmentLoginBinding.edittextMobileNum.getText().toString(), mFragmentLoginBinding.edittextPassword.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
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


}
