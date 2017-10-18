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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.interfaces.BaseInterface;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class SignUpFragment extends Fragment implements BaseInterface {

    View mView;

    Button mBtnSignup, mBtnLogin;
    TextView mTxtLogin, mTxtSignup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
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

        mTxtSignup = (TextView) mView.findViewById(R.id.textview_signup);
        mTxtLogin = (TextView) mView.findViewById(R.id.textview_login);
    }

    @Override
    public void setListeners() {
        mTxtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.login_contrainer, new FragmentOne())
                        .addToBackStack(null).commit();
            }
        });

        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }

    @Override
    public void showData() {

    }
}
