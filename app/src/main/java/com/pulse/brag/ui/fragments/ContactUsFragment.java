package com.pulse.brag.ui.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.helper.Validation;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.views.OnSingleClickListener;

/**
 * Created by nikhil.vadoliya on 30-01-2018.
 */


public class ContactUsFragment extends BaseFragment implements BaseInterface {


    View mView;
    EditText mEdtName, mEdtEmail, mEdtMessage;
    TextView mTxtSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_contact_us, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
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
                    Utility.showAlertMessage(getContext(),getString(R.string.error_empty_name));
                }else  if(Validation.isEmpty(mEdtEmail)){
                    Utility.showAlertMessage(getContext(),getString(R.string.error_please_email));
                }else if(!Validation.isEmailValid(mEdtEmail)){
                    Utility.showAlertMessage(getContext(),getString(R.string.error_email_valid));
                }else if(Validation.isEmpty(mEdtMessage)){
                    Utility.showAlertMessage(getContext(),getString(R.string.error_empty_message));
                }else if(Utility.isConnection(getContext())){
                    //api call
                    getActivity().onBackPressed();
                }else {
                    Utility.showAlertMessage(getContext(),0,null);
                }
            }
        });

    }

    @Override
    public void showData() {

    }
}
