package com.ragtagger.brag.ui.authentication.createnewpassord;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentCreateNewPasswordBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class CreateNewPasswordFragment extends CoreFragment<FragmentCreateNewPasswordBinding, CreateNewPasswordViewModel> implements CreateNewPasswordNavigator/*extends BaseFragment implements BaseInterface*/ {

    @Inject
    CreateNewPasswordViewModel mCreateNewPasswordViewModel;
    FragmentCreateNewPasswordBinding mFragmentCreateNewPasswordBinding;

    boolean isFromChangePass;
    String mobile;
    String otp;

    public static CreateNewPasswordFragment newInstance(String mobile, String otp) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        args.putString(Constants.BUNDLE_OTP, otp);
        CreateNewPasswordFragment fragment = new CreateNewPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreateNewPasswordViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mobile = getArguments().getString(Constants.BUNDLE_MOBILE);
        otp = getArguments().getString(Constants.BUNDLE_OTP);
    }

    @Override
    public void afterViewCreated() {
        mFragmentCreateNewPasswordBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentCreateNewPasswordBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public CreateNewPasswordViewModel getViewModel() {
        return mCreateNewPasswordViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_new_password;
    }

    private void showAlertMessage() {
        try {
            final Dialog alertDialog = new Dialog(getContext());

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_one_button);
            Utility.applyTypeFace(getContext(), (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(getString(R.string.msg_password_change_successfull));

            Button dialogButton = (Button) alertDialog.findViewById(R.id.button_ok_alert_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    ((SplashActivity) getActivity()).popBackToLogin();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (Validation.isEmpty(mFragmentCreateNewPasswordBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_new_pass));
        } else if (Validation.isEmpty(mFragmentCreateNewPasswordBinding.edittextConfirmPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_confirm_pass));
        } else if (!(mFragmentCreateNewPasswordBinding.edittextPassword.getText().toString().equals(mFragmentCreateNewPasswordBinding.edittextConfirmPassword.getText().toString()))) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_password_not_match));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mCreateNewPasswordViewModel.changePassword(mobile, otp, mFragmentCreateNewPasswordBinding.edittextPassword.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }
    }

    @Override
    public void onChangePasswordSuccess() {
        showAlertMessage();
    }
}
