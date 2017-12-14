package com.pulse.brag.activities;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.fragments.ChangeMobileNumberFragment;
import com.pulse.brag.fragments.ChangePasswordFragment;
import com.pulse.brag.fragments.ForgetPasswordFragment;
import com.pulse.brag.fragments.LogInFragment;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.views.OnSingleClickListener;




/*
* In this activity ,toolbar have transparent and layout of toolbar and framelayout are relative not linear so not extend
* to BaseActivity and make new progressbar
*/

/**
 * Created by nikhil.vadoliya on 20-11-2017.
 */


public class ChangePasswordOrMobileActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private ImageView mImgBack;
    private TextView mTxtToolbarTitle;

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passoword);

        init();
        setListener();

        if (getIntent().hasExtra(Constants.BUNDLE_MOBILE)) {
            pushFragmentInChangeContainer(ChangePasswordFragment.newInstance(getIntent().getStringExtra(Constants.BUNDLE_MOBILE))
                    , false, false, "Change_Pass");

        } else {
            pushFragmentInChangeContainer(new ForgetPasswordFragment(), false, false, "Change_Mobile");
        }

    }

    private void setListener() {

        mImgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });
    }


    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_change_pass);
        mImgBack = (ImageView) mToolbar.findViewById(R.id.imageView_back);
        mTxtToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);

        fragmentManager = getSupportFragmentManager();
        Utility.applyTypeFace(getApplicationContext(), (LinearLayout) findViewById(R.id.base_layout));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    public void showToolBar(String title) {
        mTxtToolbarTitle.setText(title);
    }

    public void pushFragmentInChangeContainer(Fragment fragment, boolean isAddBackStack, boolean isAnimation, String tag) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAnimation) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                    R.anim.enter_from_left, R.anim.exit_to_right);

        }
        if (isAddBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.fragment_change_container, fragment);

        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }
}
