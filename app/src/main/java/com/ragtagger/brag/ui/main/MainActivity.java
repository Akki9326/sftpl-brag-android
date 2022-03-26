package com.ragtagger.brag.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.ActivityMainBinding;
import com.ragtagger.brag.ui.cart.CartFragment;
import com.ragtagger.brag.ui.home.HomeFragment;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends ToolbarActivity<MainActivity, ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {
    @Inject
    MainViewModel mMainViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityMainBinding mMainActivyBinding;
    FragmentManager manager;

    long mLastBack = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Clean fragments (only if the app is recreated (When user disable permission))
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Intent intent = getIntent();
            startActivity(intent);
            finish();
            return;
        }
        mMainViewModel.setNavigator(this);
    }

    @Override
    public void beforeLayoutSet() {

    }

    @Override
    public void afterLayoutSet() {
        super.afterLayoutSet();
        mMainActivyBinding = getViewDataBinding();

        Utility.applyTypeFace(getApplicationContext(), mMainActivyBinding.baseLayout);
        pushFragments(HomeFragment.getInstance(), false, false);
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

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
        pushFragments(fragment, shouldAnimate, shouldAdd, null);
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


    public void updateCartNum() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setBadgeCount(BragApp.CartNumber);
            }
        }, 500);

    }

    public void clearStackForPlaceOrder() {
        getSupportFragmentManager().popBackStackImmediate("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void performCartClick() {
        super.performCartClick();
        pushFragments(new CartFragment(), true, true, "Cart");
    }

    @Override
    public void performBackClick() {
        super.performBackClick();
        Utility.hideSoftkeyboard(MainActivity.this);
        onBackPressed();
    }

    @Override
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

    }
}
