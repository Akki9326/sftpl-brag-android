package com.ragtagger.brag.ui.contactus;


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

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentContactUsBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

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
    public void performClickSend() {
        if (isAdded())
            mContactUsViewModel.validateContactUsForm(getActivity(), mFragmentContactUsBinding.edittextName, mFragmentContactUsBinding.edittextEmail, mFragmentContactUsBinding.edittextMessage);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validContactUsForm() {
        showProgress();
        mContactUsViewModel.callSendMessageApi(mFragmentContactUsBinding.edittextName.getText().toString(), mFragmentContactUsBinding.edittextEmail.getText().toString(), mFragmentContactUsBinding.edittextMessage.getText().toString());
    }

    @Override
    public void invalidContactUsForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void goBack() {
        AlertUtils.showAlertMessageWithButton(getActivity(), getString(R.string.message_contact_us_success), new AlertUtils.IDismissDialogListener() {
            @Override
            public void dismissDialog(Dialog dialog) {
                dialog.dismiss();
                getActivity().onBackPressed();
            }
        });
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
