package com.pulse.brag.ui.contactus;


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

    /*View mView;
    EditText mEdtName, mEdtEmail, mEdtMessage;
    TextView mTxtSend;*/

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_contact_us, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }*/

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

    /*@Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {

        mEdtEmail = mView.findViewById(R.id.edittext_email);
        mEdtMessage = mView.findViewById(R.id.edittext_message);
        mEdtName = mView.findViewById(R.id.edittext_name);

        mTxtSend = mView.findViewById(R.id.textview_send);
    }

    @Override
    public void setListeners() {

        mTxtSend.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (Validation.isEmpty(mEdtName)){
                    //Utility.showAlertMessage(getContext(),getString(R.string.error_empty_name));
                    AlertUtils.showAlertMessage(getContext(),getString(R.string.error_empty_name));
                }else  if(Validation.isEmpty(mEdtEmail)){
                    //Utility.showAlertMessage(getContext(),getString(R.string.error_please_email));
                    AlertUtils.showAlertMessage(getContext(),getString(R.string.error_please_email));
                }else if(!Validation.isEmailValid(mEdtEmail)){
                    //Utility.showAlertMessage(getContext(),getString(R.string.error_email_valid));
                    AlertUtils.showAlertMessage(getContext(),getString(R.string.error_email_valid));
                }else if(Validation.isEmpty(mEdtMessage)){
                    //Utility.showAlertMessage(getContext(),getString(R.string.error_empty_message));
                    AlertUtils.showAlertMessage(getContext(),getString(R.string.error_empty_message));
                }else if(Utility.isConnection(getContext())){
                    //api call
                    getActivity().onBackPressed();
                }else {
                    //Utility.showAlertMessage(getContext(),0,null);
                    AlertUtils.showAlertMessage(getContext(),0,null);
                }
            }
        });

    }

    @Override
    public void showData() {

    }*/

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
    public void send() {
        if (Validation.isEmpty(mFragmentContactUsBinding.edittextName)) {
            AlertUtils.showAlertMessage(getContext(), getString(R.string.error_empty_name));
        } else if (Validation.isEmpty(mFragmentContactUsBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getContext(), getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(mFragmentContactUsBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getContext(), getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(mFragmentContactUsBinding.edittextMessage)) {
            AlertUtils.showAlertMessage(getContext(), getString(R.string.error_empty_message));
        } else if (Utility.isConnection(getContext())) {
            //api call
            getActivity().onBackPressed();
        } else {
            AlertUtils.showAlertMessage(getContext(), 0, null);
        }
    }
}
