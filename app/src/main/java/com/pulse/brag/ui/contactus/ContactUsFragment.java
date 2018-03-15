package com.pulse.brag.ui.contactus;


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
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentContactUsBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 30-01-2018.
 */


public class ContactUsFragment extends CoreFragment<FragmentContactUsBinding, ContactUsViewModel> implements ContactUsNavigator/*implements BaseInterface*/ {

    @Inject
    ContactUsViewModel mContactUsViewModel;
    FragmentContactUsBinding mFragmentContactUsBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactUsViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentContactUsBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentContactUsBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public ContactUsViewModel getViewModel() {
        return mContactUsViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_us;
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

    @Override
    public void send() {
        if (Validation.isEmpty(mFragmentContactUsBinding.edittextName)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_name));
        } else if (Validation.isEmpty(mFragmentContactUsBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(mFragmentContactUsBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(mFragmentContactUsBinding.edittextMessage)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_message));
        } else if (Utility.isConnection(getContext())) {
            showProgress();
            mContactUsViewModel.sendMessage(mFragmentContactUsBinding.edittextName.getText().toString(), mFragmentContactUsBinding.edittextEmail.getText().toString(), mFragmentContactUsBinding.edittextMessage.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void back() {
        AlertUtils.showAlertMessageWithButton(getActivity(), getString(R.string.message_contact_us_success), new AlertUtils.IDismissDialogListener() {
            @Override
            public void dismissDialog(Dialog dialog) {
                dialog.dismiss();
                getActivity().onBackPressed();
            }
        });

    }
}
