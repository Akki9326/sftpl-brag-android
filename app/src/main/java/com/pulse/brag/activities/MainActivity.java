package com.pulse.brag.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pulse.brag.BragApp;
import com.pulse.brag.fragments.HomeFragment;
import com.pulse.brag.R;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.pojo.requests.AddToCartRequest;

public class MainActivity extends BaseActivity {

    long mLastBack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);


        BragApp.NotificationNumber = 0;
        setBagCount(BragApp.CartNumber);
        pushFragments(new HomeFragment(), false, false);


    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (SystemClock.elapsedRealtime() - mLastBack < Constants.EXIT_TIME_INTERVAL) {
                super.onBackPressed();
            } else {
                mLastBack = SystemClock.elapsedRealtime();
//                Snackbar mSnackbar = Snackbar.make((FrameLayout) findViewById(R.id.fragment_container), getString(R.string.msg_back_to_exit_app), Snackbar.LENGTH_SHORT);
//                mSnackbar.show();
                Toast.makeText(this, "" + getString(R.string.msg_back_to_exit_app), Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            super.onBackPressed();
        }
    }


    public void addToCartAPI(AddToCartRequest addToCartRequest) {
        showProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                BragApp.CartNumber++;
                setBagCount(BragApp.CartNumber);
            }
        }, 1000);

    }


}

