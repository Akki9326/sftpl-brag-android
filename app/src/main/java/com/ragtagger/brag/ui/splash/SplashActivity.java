package com.ragtagger.brag.ui.splash;

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

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.databinding.ActivitySplashBinding;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.authentication.login.LogInFragment;
import com.ragtagger.brag.ui.authentication.signup.complete.SignUpCompleteFragment;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.ui.main.MainActivity;

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
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (manager.findFragmentById(R.id.fragment_container_login) instanceof SignUpCompleteFragment) {
            return;
        } else {
            super.onBackPressed();
        }
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
        mActiviSplashBinding.imageviewLogo.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mActiviSplashBinding.fragmentContainerLogin.setVisibility(View.VISIBLE);
                openLoginFragment();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
