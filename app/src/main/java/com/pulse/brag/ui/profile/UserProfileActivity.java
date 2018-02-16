package com.pulse.brag.ui.profile;


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
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.ActivityUserProfileBinding;
import com.pulse.brag.ui.profile.changepassword.ChangePassFragment;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.forgotpassword.ForgetPasswordFragment;
import com.pulse.brag.ui.profile.updateprofile.UpdateProfileFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;




/*
* In this activity ,toolbar have transparent and layout of toolbar and framelayout(container) are relative not linear so not extend
* to BaseActivity and make new progressbar
*
*
* Why new Activity -new Toolbar ,Toolbar and FrameLayout(container) have parent RelativeLayout
 * Why not extend to BaseActivty- new Toolbar
*/

/**
 * Created by nikhil.vadoliya on 20-11-2017.
 */


public class UserProfileActivity extends CoreActivity<UserProfileActivity, ActivityUserProfileBinding, UserProfileViewModel> implements UserProfileNavigator, CoreActivity.OnToolbarSetupListener, HasSupportFragmentInjector {

    @Inject
    UserProfileViewModel mChangePassMobViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityUserProfileBinding mActivityUserProfileBinding;


    /*private Toolbar mToolbar;
    private ImageView mImgBack;
    private TextView mTxtToolbarTitle;*/

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_profile);
        //init();
        //setListener();
        //Utility.applyTypeFace(getApplicationContext(), (LinearLayout) findViewById(R.id.base_layout));
    }

    @Override
    public UserProfileViewModel getViewModel() {
        return mChangePassMobViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    public void beforeLayoutSet() {

    }

    @Override
    public void afterLayoutSet() {
        mActivityUserProfileBinding = getViewDataBinding();
        mChangePassMobViewModel.setNavigator(this);

        Utility.applyTypeFace(getApplicationContext(), (LinearLayout) mActivityUserProfileBinding.baseLayout);
        fragmentManager = getSupportFragmentManager();
        mChangePassMobViewModel.decideNextFragment(getIntent().getIntExtra(Constants.BUNDLE_PROFILE_IS_FROM, 0));
    }

    /*private void setListener() {

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
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    public void showToolBar(String title) {
        //mTxtToolbarTitle.setText(title);
        mChangePassMobViewModel.updateToolbarTitle(title);
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

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivityInstance(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void backPress() {
        onBackPressed();
    }

    @Override
    public void pushChangePassFragment() {
        pushFragmentInChangeContainer(ChangePassFragment.newInstance(getIntent().getStringExtra(Constants.BUNDLE_MOBILE))
                , false, false, "Change_Pass");
    }

    @Override
    public void pushForgotPassFragment() {
        pushFragmentInChangeContainer(new ForgetPasswordFragment(), false, false, "Change_Mobile");
    }

    @Override
    public void pushUserProfileFragment() {
        pushFragmentInChangeContainer(UpdateProfileFragment.newInstance(getIntent().getStringExtra(Constants.BUNDLE_MOBILE))
                , false, false, "Update_Profile");
    }

    @Override
    public void setUpToolbar() {
        mChangePassMobViewModel.updateToolbarTitle(getString(R.string.label_change_pass));
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
