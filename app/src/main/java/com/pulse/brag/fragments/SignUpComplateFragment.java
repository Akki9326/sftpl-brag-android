package com.pulse.brag.fragments;


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
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.interfaces.BaseInterface;

/**
 * Created by nikhil.vadoliya on 09-11-2017.
 */


public class SignUpComplateFragment extends BaseFragment implements BaseInterface {

    View mView;
    TextView mTxtLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_signup_complate, container, false);
            initializeData();
            setListeners();

        }
        return mView;
    }

    private void init() {
    }

    @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {
        mTxtLogin = (TextView) mView.findViewById(R.id.textview_login);

    }

    @Override
    public void setListeners() {
        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SplashActivty) getActivity()).popBackToLogin();
            }
        });
    }

    @Override
    public void showData() {

    }
}
