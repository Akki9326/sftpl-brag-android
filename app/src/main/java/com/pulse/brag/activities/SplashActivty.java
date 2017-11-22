package com.pulse.brag.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pulse.brag.FCMInstanceIDService;
import com.pulse.brag.R;
import com.pulse.brag.fragments.LogInFragment;
import com.pulse.brag.fragments.SignUpComplateFragment;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;

public class SplashActivty extends AppCompatActivity {

    ImageView mImgLogo;
    FrameLayout mFrameLayout;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_activty);

        Utility.applyTypeFace(getApplicationContext(), (RelativeLayout) findViewById(R.id.base_layout));

        init();
        setDeviceNameAndOS();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PreferencesManager.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActivty.this, MainActivity.class));
                    finish();
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } else {
                    animatedViewAndLogin();
                }

            }
        }, 2000);
    }



    private void animatedViewAndLogin() {
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_view);
        mImgLogo.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFrameLayout.setVisibility(View.VISIBLE);
                pushFragments(new LogInFragment(), true, false, "");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void init() {
        mImgLogo = (ImageView) findViewById(R.id.imageview_logo);
        mFrameLayout = (FrameLayout) findViewById(R.id.login_contrainer);
    }


    private void setDeviceNameAndOS() {
        if (PreferencesManager.getInstance().getDeviceType().isEmpty()) {
            PreferencesManager.getInstance().setDeviceTypeAndOsVer(Utility.getDeviceName(), android.os.Build.VERSION.RELEASE);
        }
        Log.i(getClass().getSimpleName(), "setDeviceNameAndOS: device token " + FirebaseInstanceId.getInstance().getToken());
        PreferencesManager.getInstance().setDeviceToken(FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public void onBackPressed() {
        if (manager.findFragmentById(R.id.login_contrainer) instanceof SignUpComplateFragment) {
            return;
        } else {
            super.onBackPressed();
        }

//        if (manager.getBackStackEntryCount() == 3 || manager.findFragmentById(R.id.login_contrainer) instanceof SignUpComplateFragment) {
//            manager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        } else {
//            super.onBackPressed();
//        }
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd, String TAG) {

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {

            if (fragment instanceof LogInFragment) {
                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            } else {
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right);
            }
        }
        if (shouldAdd) {
            ft.addToBackStack(TAG);
        }
        ft.replace(R.id.login_contrainer, fragment);

        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    public void popBackToLogin() {
        manager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
