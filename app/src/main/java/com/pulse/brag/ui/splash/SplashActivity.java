package com.pulse.brag.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.databinding.ActivitySplashBinding;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.login.LogInFragment;
import com.pulse.brag.ui.signup.complete.SignUpCompleteFragment;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SplashActivity extends CoreActivity<SplashActivity, ActivitySplashBinding, SplashViewModel> implements SplashNavigator, HasSupportFragmentInjector {

    @Inject
    SplashViewModel mSplashViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivitySplashBinding mActiviSplashBinding;

    /*ImageView mImgLogo;
    FrameLayout mFrameLayout;*/
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        //init();
        //setDeviceNameAndOS();
    }


    /*private void animatedViewAndLogin() {
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
                openLoginFragment();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }*/

    /*private void init() {
        mImgLogo = findViewById(R.id.imageview_logo);
        mFrameLayout = findViewById(R.id.fragment_container_login);
    }*/


/*    private void setDeviceNameAndOS() {
        if (PreferencesManager.getInstance().getDeviceType().isEmpty()) {
            PreferencesManager.getInstance().setDeviceTypeAndOsVer(Utility.getDeviceName(), android.os.Build.VERSION.RELEASE);
        }
        AppLogger.i(getClass().getSimpleName()+" : setDeviceNameAndOS: device token " + FirebaseInstanceId.getInstance().getToken());
        PreferencesManager.getInstance().setDeviceToken(FirebaseInstanceId.getInstance().getToken());
    }*/


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
        ft.replace(R.id.fragment_container_login, fragment);

        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    private boolean checkAndRequestPermissions() {
        boolean hasPermissionSendMessage = hasPermission(Manifest.permission.SEND_SMS);
        boolean hasPermissionReceiveSMS = hasPermission(Manifest.permission.RECEIVE_MMS);
        boolean hasPermissionReadSMS = hasPermission(Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (!hasPermissionSendMessage) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!hasPermissionReceiveSMS) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (!hasPermissionReadSMS) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermission(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Constants.IPermissionRequestCode.REQ_SMS_SEND_RECEIVED_READ);
            return false;
        }
        return true;
    }

    public void openLoginFragment() {
        pushFragments(new LogInFragment(), true, false, "");
        checkAndRequestPermissions();
    }

    public void popBackToLogin() {
        manager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackPressed() {
        if (manager.findFragmentById(R.id.fragment_container_login) instanceof SignUpCompleteFragment) {
            return;
        } else {
            super.onBackPressed();
        }
        //if (manager.getBackStackEntryCount() == 3 || manager.findFragmentById(R.id.login_contrainer) instanceof SignUpCompleteFragment) {
        //manager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //} else {
        //super.onBackPressed();
        //}
    }


    @Override
    public void openMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void openLoginFragmentWithAnimation() {
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_view);
        //mImgLogo.startAnimation(slide);
        mActiviSplashBinding.imageviewLogo.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //mFrameLayout.setVisibility(View.VISIBLE);
                mActiviSplashBinding.fragmentContainerLogin.setVisibility(View.VISIBLE);
                openLoginFragment();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void beforeLayoutSet() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void afterLayoutSet() {
        mActiviSplashBinding = getViewDataBinding();
        mSplashViewModel.setNavigator(this);

        Utility.applyTypeFace(getApplicationContext(), (RelativeLayout) mActiviSplashBinding.baseLayout);
        mSplashViewModel.setDeviceNameAndOS();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSplashViewModel.decideNextActivity();
            }
        }, 2000);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
