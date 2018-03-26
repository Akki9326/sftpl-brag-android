package com.ragtagger.brag.ui.main;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.databinding.ActivityMainBinding;
import com.ragtagger.brag.ui.cart.CartFragment;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.home.HomeFragment;
import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends CoreActivity<MainActivity, ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, CoreActivity.OnToolbarSetupListener {

    long mLastBack = 0;


    @Inject
    MainViewModel mMainViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityMainBinding mMainActivyBinding;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public MainViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void beforeLayoutSet() {

    }

    @Override
    public void afterLayoutSet() {
        mMainActivyBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        Utility.applyTypeFace(getApplicationContext(),mMainActivyBinding.baseLayout);
        // TODO: 16-02-2018 move to core activity
        if (bActivity instanceof OnToolbarSetupListener) {
            ((OnToolbarSetupListener) bActivity).setUpToolbar();
        }
        pushFragments(new HomeFragment(), false, false);
    }


    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {

        manager = getSupportFragmentManager();
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

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd, String tag) {

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
            ft.setCustomAnimations(R.anim.right_in, R.anim.left_out,
                    R.anim.left_in, R.anim.right_out);
        }
        if (shouldAdd) {
            ft.addToBackStack(tag);
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (SystemClock.elapsedRealtime() - mLastBack < Constants.EXIT_TIME_INTERVAL) {
                super.onBackPressed();
            } else {
                mLastBack = SystemClock.elapsedRealtime();
                Toast.makeText(this, "" + getString(R.string.msg_back_to_exit_app), Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            super.onBackPressed();
        }
    }


    public void updateCartNum() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setBagCount(BragApp.CartNumber);
            }
        }, 500);

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void setUpToolbar() {

        setUpToolbar(mMainActivyBinding.toolbar.toolbar
                , mMainActivyBinding.toolbar.toolbarTitle
                , mMainActivyBinding.toolbar.imageViewBack
                , mMainActivyBinding.toolbar.imageViewLogo
                , mMainActivyBinding.toolbar.linearCard
                , mMainActivyBinding.toolbar.badgeTvToolbar
                , mMainActivyBinding.toolbar.relativeText
                , mMainActivyBinding.toolbar.textviewReadAll);


        mMainActivyBinding.toolbar.linearCard.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                pushFragments(new CartFragment(), true, true, "Cart");
            }
        });


    }

    public void clearStackForPlaceOrder() {
        getSupportFragmentManager().popBackStackImmediate("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onRestart();
    }
}

