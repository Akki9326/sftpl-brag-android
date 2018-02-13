package com.pulse.brag.ui.activities;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pulse.brag.BragApp;
import com.pulse.brag.R;
import com.pulse.brag.ui.fragments.CartFragment;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.CustomProgressDialog;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class BaseActivity extends AppCompatActivity {

    private long mLastClickTimeListViewItem = 0;
    private long mLastClick = 0;

    Toolbar mToolbar;
    TextView mToolbarTitle, mTxtBagCount;
    public TextView mTxtReadAll;
    ImageView mImgBack;
    ImageView mImgLogo;

    LinearLayout mLinearCart;
    CustomProgressDialog mProgressDialog;
    Fragment fragmentName;
    RelativeLayout mRelText;

    protected void onCreate(@Nullable Bundle savedInstanceState, int layout) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mProgressDialog = new CustomProgressDialog(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mImgBack = (ImageView) mToolbar.findViewById(R.id.imageView_back);
        mImgLogo = (ImageView) mToolbar.findViewById(R.id.imageView_logo);
        mLinearCart = (LinearLayout) mToolbar.findViewById(R.id.linear_card);
        mTxtBagCount = (TextView) mToolbar.findViewById(R.id.badge_tv_toolbar);
        mRelText = (RelativeLayout) mToolbar.findViewById(R.id.relative_text);
        mTxtReadAll = mToolbar.findViewById(R.id.textview_read_all);
        Utility.applyTypeFace(getApplicationContext(), mToolbar);


        mLinearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragments(new CartFragment(), true, true);
            }
        });
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideSoftkeyboard(BaseActivity.this, view);
                onBackPressed();
            }
        });
    }

    public void setBagCount(int num) {
        if (num == 0) {
            mTxtBagCount.setVisibility(View.GONE);
        } else {
            mTxtBagCount.setVisibility(View.VISIBLE);
            mTxtBagCount.setText(Utility.getBadgeNumber(num));
        }
    }

    public void setNavigationBarTitle(String title) {
        mToolbarTitle.setText(title);
        mImgBack.setVisibility(View.GONE);
        mImgLogo.setVisibility(View.GONE);
    }

    public void showToolbar(boolean isBackBtn, boolean isLogo, boolean isCardBtn) {

        mTxtReadAll.setVisibility(View.GONE);
        if (isBackBtn) {
            mImgBack.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
        } else {
            mImgBack.setVisibility(View.GONE);
        }
        if (isLogo) {
            mImgLogo.setVisibility(View.VISIBLE);
            mToolbarTitle.setVisibility(View.GONE);
            mRelText.setVisibility(View.GONE);
        } else {
            mToolbarTitle.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
            mImgLogo.setVisibility(View.GONE);
        }
        if (isCardBtn) {
            mLinearCart.setVisibility(View.VISIBLE);
        } else {
            mLinearCart.setVisibility(View.GONE);
        }
    }

    public void showToolbar(boolean isBack, boolean isLogo, boolean isCard, String title) {

        showToolbar(isBack, isLogo, isCard);
        if (!isLogo)
            mToolbarTitle.setText(title);

    }

    public void showToolbar(boolean isBack, boolean isLogo, String title, String rightLabel) {
        mTxtReadAll.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(title);
        mTxtReadAll.setText(rightLabel);
        if (isBack) {
            mImgBack.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
        } else {
            mImgBack.setVisibility(View.GONE);
        }
        if (isLogo) {
            mImgLogo.setVisibility(View.VISIBLE);
            mToolbarTitle.setVisibility(View.GONE);
            mRelText.setVisibility(View.GONE);
        } else {
            mToolbarTitle.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
            mImgLogo.setVisibility(View.GONE);
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    public void fullScreenEnable(boolean b) {
        if (b) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mToolbar.setVisibility(View.GONE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
            ft.setCustomAnimations(R.anim.right_in, R.anim.left_out,
                    R.anim.left_in, R.anim.right_out);
        }
        if (shouldAdd) {
            ft.addToBackStack(null);
        }
        ft.replace(R.id.fragment_container, fragment);

        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    public void pushFragmentsAdd(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
            ft.setCustomAnimations(R.anim.right_in, R.anim.left_out,
                    R.anim.left_in, R.anim.right_out);
        }
        if (shouldAdd) {
            ft.addToBackStack(null);
        }

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            ft.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        }

        ft.add(R.id.fragment_container, fragment);

        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    public void pushFragments(final Bundle args,
                              final Fragment fragment, final Fragment fragmentParent, boolean shouldAnimate,
                              final boolean shouldAdd, final boolean ignoreIfCurrent) {

        try {
            Utility.hideSoftkeyboard(BaseActivity.this);
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_category);

            if (ignoreIfCurrent && currentFragment != null && fragment != null && currentFragment.getClass().equals(fragment.getClass())) {
                return;
            }

            if (fragment.getArguments() == null) {
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager;

            if (fragmentParent == null) {
                fragmentManager = getSupportFragmentManager();
            } else {
                fragmentManager = fragmentParent.getChildFragmentManager();
            }


            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (shouldAdd) {
                ft.addToBackStack(null);
            }

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (shouldAnimate)
                ft.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);

            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container_category) != null) {
                System.out.println("my==>" + BaseActivity.this.getSupportFragmentManager().findFragmentById(R.id.fragment_container_category));
                ft.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container_category));
                ft.hide(fragmentName);
            }
            getFragmentManager().executePendingTransactions();
            if (fragment.isAdded()) {
                ft.show(fragment);
            } else {
                fragmentName = fragment;
                ft.add(R.id.fragment_container_category, fragment, fragment.getClass().getCanonicalName());
            }

//            ft.commitAllowingStateLoss();
            if (SystemClock.elapsedRealtime() - mLastClickTimeListViewItem < 1000) {
                return;
            }
            mLastClickTimeListViewItem = SystemClock.elapsedRealtime();
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(int exitType) {
        switch (exitType) {
            case Constants.EXIT_LEFT:
                super.onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case Constants.EXIT_BOTTOM:
                super.onBackPressed();
                overridePendingTransition(R.anim.hold, R.anim.push_out_to_bottom);
                break;
            default:
        }
    }

    public void simpleFragmentPush(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(id, fragment)
                .addToBackStack("")
                .commit();
    }

    public void gotoActivityWithAnimation(Intent intent, boolean doFinish) {
        startActivity(intent);
        if (doFinish)
            finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }


    public void showProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing())
                    hideProgressDialog();
                mProgressDialog.show("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss("");
            }
        } catch (Exception e) {

        }

    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    public void clearAllStackFragment() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }


    public void setToolbarTransparent(boolean b) {
        if (b)
            mToolbar.setBackgroundColor(getResources().getColor(R.color.semi_transparent));
//            mToolbar.setBackgroundColor(android.R.color.transparent);
        else
            mToolbar.setVisibility(getResources().getColor(R.color.white));
//        mToolbar.setBackgroundColor(R.color.white);
    }

    public String getNotificationlabel() {
        if (BragApp.NotificationNumber > 0) {
            return getString(R.string.toolbar_label_notification) + " (" + Utility.getBadgeNumber(BragApp.NotificationNumber) + ")";
        } else {
            return getString(R.string.toolbar_label_notification);
        }
    }
}
