package com.pulse.brag.ui.forgotpassword;


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
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentForgetPassBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.otp.OTPFragment;
import com.pulse.brag.ui.profile.UserProfileActivity;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.pulse.brag.utils.Constants.IPermissionRequestCode.REQ_SMS_SEND_RECEIVED_READ;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class ForgetPasswordFragment extends CoreFragment<FragmentForgetPassBinding, ForgotPasswordViewModel>/*BaseFragment implements BaseInterface*/ implements ForgotPasswordNavigator {

    @Inject
    ForgotPasswordViewModel mForgotPasswordViewModel;
    FragmentForgetPassBinding mFragmentForgotPassBinding;

    /*View mView;
    EditText mEdtMobile;
    TextView mTxtSendOtp, mTxtMobileNum;*/


    public static ForgetPasswordFragment newInstance(String mobile) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_forget_pass, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForgotPasswordViewModel.setNavigator(this);
    }

    /**
     * Get bundle data here
     */
    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentForgotPassBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentForgotPassBinding.baseLayout);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_MOBILE)) {
            if (getArguments().getString(Constants.BUNDLE_MOBILE).trim().isEmpty()
                    || getArguments().getString(Constants.BUNDLE_MOBILE).length() < 10) {
                //mFragmentForgotPassBinding.edittextMobileNum.setText("");
                mForgotPasswordViewModel.setMobileNumber("");
            } else {
                //mFragmentForgotPassBinding.edittextMobileNum.setText(getArguments().getString(Constants.BUNDLE_MOBILE));
                mForgotPasswordViewModel.setMobileNumber(getArguments().getString(Constants.BUNDLE_MOBILE));
            }
        }
        if (getActivity() instanceof UserProfileActivity) {
            mFragmentForgotPassBinding.edittextMobileNum.setHint(getString(R.string.hint_new_mobile_num));
            mFragmentForgotPassBinding.textviewMobileNum.setText(getString(R.string.label_new_mobile_no));
        }
    }

    @Override
    public void setUpToolbar() {
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_change_mobile_num));
        }
    }

    @Override
    public ForgotPasswordViewModel getViewModel() {
        return mForgotPasswordViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_pass;
    }

    @Override
    public void onPermissionGranted(int request) {
        super.onPermissionGranted(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ)
            mForgotPasswordViewModel.sendOtp(mFragmentForgotPassBinding.edittextMobileNum.getText().toString());
    }

    @Override
    public void onPermissionDenied(int request) {
        super.onPermissionDenied(request);
        if (request == REQ_SMS_SEND_RECEIVED_READ)
            mForgotPasswordViewModel.sendOtp(mFragmentForgotPassBinding.edittextMobileNum.getText().toString());
    }

    /*Commented by alpesh on 14/02/18 to convert mvvm architecture*/
    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_change_mobile_num));
        }
    }

    @Override
    public void initializeData() {

        mEdtMobile = (EditText) mView.findViewById(R.id.edittext_mobile_num);
        mTxtSendOtp = (TextView) mView.findViewById(R.id.textview_send_otp);
        mTxtMobileNum = (TextView) mView.findViewById(R.id.textview_mobile_num);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_MOBILE)) {
            if (getArguments().getString(Constants.BUNDLE_MOBILE).trim().isEmpty()
                    || getArguments().getString(Constants.BUNDLE_MOBILE).length() < 10) {
                mEdtMobile.setText("");
            } else {
                mEdtMobile.setText(getArguments().getString(Constants.BUNDLE_MOBILE));
            }
        }
        if (getActivity() instanceof UserProfileActivity) {
            mEdtMobile.setHint(getString(R.string.hint_new_mobile_num));
            mTxtMobileNum.setText(getString(R.string.label_new_mobile_no));
        }

    }

    @Override
    public void setListeners() {

        mTxtSendOtp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (Validation.isEmpty(mEdtMobile)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
                } else if (!Validation.isValidMobileNum(mEdtMobile)) {
                    //Utility.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                    AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
                } else if (Utility.isConnection(getActivity())) {
                    ForgetPassAPI(mEdtMobile.getText().toString());
                } else {
                    //Utility.showAlertMessage(getActivity(), 0, null);
                    AlertUtils.showAlertMessage(getActivity(), 0, null);
                }
            }
        });
    }

    @Override
    public void showData() {

    }

    private void ForgetPassAPI(String s) {
        showProgressDialog();
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<GeneralResponse> responeCall = ApiClient.getInstance(getActivity()).getApiResp().resendOtp(s);
        responeCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralResponse respone = response.body();
                    if (respone.isStatus()) {
                        // TODO: 13-11-2017 email address pass for display in otp screen
                        if (getActivity() instanceof SplashActivity) {
                            ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mEdtMobile.getText().toString(),
                                    "email@email.com", OTPValidationIsFrom.FORGET_PASS.ordinal()),
                                    true, true, "OTP_frag");
                        } else {
                            ((UserProfileActivity) getActivity()).pushFragmentInChangeContainer(OTPFragment.newInstance(mEdtMobile.getText().toString(),
                                    "email@email.com", OTPValidationIsFrom.CHANGE_MOBILE.ordinal()),
                                    true, true, "OTP_frag");
                        }

                    } else {
                        //Utility.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getActivity(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });
    }*/
    /*End comment*/

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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void sendOtp() {
        if (Validation.isEmpty(mFragmentForgotPassBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mFragmentForgotPassBinding.edittextMobileNum)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_mobile_valid));
        } else if (Utility.isConnection(getActivity())) {
            // TODO: 2/26/2018 Add sms permission
            if (checkAndRequestPermissions()) {
                mForgotPasswordViewModel.sendOtp(mFragmentForgotPassBinding.edittextMobileNum.getText().toString());
            }


        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void pushOtpFragment() {
        // TODO: 13-11-2017 email address pass for display in otp screen
        if (getActivity() instanceof SplashActivity) {
            pushFragmentOnSplash();
        } else {
            pushFragmentOnChangePassword();
        }
    }

    @Override
    public void pushFragmentOnSplash() {
        ((SplashActivity) getActivity()).pushFragments(OTPFragment.newInstance(mFragmentForgotPassBinding.edittextMobileNum.getText().toString(),
                "email@email.com", Constants.OTPValidationIsFrom.FORGET_PASS.ordinal()),
                true, true, "OTP_frag");

    }

    @Override
    public void pushFragmentOnChangePassword() {
        ((UserProfileActivity) getActivity()).pushFragmentInChangeContainer(OTPFragment.newInstance(mFragmentForgotPassBinding.edittextMobileNum.getText().toString(),
                "email@email.com", Constants.OTPValidationIsFrom.CHANGE_MOBILE.ordinal()),
                true, true, "OTP_frag");
    }
}
