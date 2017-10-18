package com.pulse.brag.fragments;

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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.activities.MainActivity;
import com.pulse.brag.R;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class LogInFragment extends Fragment implements BaseInterface {


    View mView;
    TextView mTxtLogin, mTxtSignUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_login, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void setToolbar() {
    }

    @Override
    public void initializeData() {
        mTxtLogin = (TextView) mView.findViewById(R.id.textview_login);
        mTxtSignUp = (TextView) mView.findViewById(R.id.textview_signup);
    }

    @Override
    public void setListeners() {
        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PreferencesManager.getInstance().setIsLogin(true);

                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        mTxtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.login_contrainer, new SignUpFragment())
                        .addToBackStack("SignUpFrag").commit();
            }
        });
    }

    @Override
    public void showData() {
        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
    }
}
