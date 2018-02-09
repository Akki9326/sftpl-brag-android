package com.pulse.brag.ui.splash;

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

import com.google.firebase.iid.FirebaseInstanceId;
import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.databinding.ActivitySplashBinding;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.fragments.LogInFragment;
import com.pulse.brag.ui.fragments.SignUpComplateFragment;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.ui.activities.MainActivity;
import com.pulse.brag.utils.AppLogger;

import javax.inject.Inject;

public class SplashActivity extends CoreActivity<SplashActivity,ActivitySplashBinding,SplashViewModel> implements SplashNavigator {

    @Inject
    SplashViewModel mSplashViewModel;

    ActivitySplashBinding mActiviSplashBinding;

    /*ImageView mImgLogo;
    FrameLayout mFrameLayout;*/
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActiviSplashBinding=getViewDataBinding();
        mSplashViewModel.setNavigator(this);
        //setContentView(R.layout.activity_splash);

        Utility.applyTypeFace(getApplicationContext(), (RelativeLayout) mActiviSplashBinding.baseLayout);
        //init();
        //setDeviceNameAndOS();
        mSplashViewModel.setDeviceNameAndOS();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*if (PreferencesManager.getInstance().isLogin()) {
                    openMainActivity();
                } else {
                    animatedViewAndLogin();
                }*/
                mSplashViewModel.decideNextActivity();
            }
        }, 2000);
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
    public void openLoginFragment() {
        pushFragments(new LogInFragment(), true, false, "");
    }

    public void popBackToLogin() {
        manager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackPressed() {
        if (manager.findFragmentById(R.id.fragment_container_login) instanceof SignUpComplateFragment) {
            return;
        } else {
            super.onBackPressed();
        }
        //if (manager.getBackStackEntryCount() == 3 || manager.findFragmentById(R.id.login_contrainer) instanceof SignUpComplateFragment) {
        //manager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //} else {
        //super.onBackPressed();
        //}
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

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

}
