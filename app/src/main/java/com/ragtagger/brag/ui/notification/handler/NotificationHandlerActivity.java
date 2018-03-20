package com.ragtagger.brag.ui.notification.handler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.ActivityNotificationHandlerBinding;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.order.orderdetail.OrderDetailFragment;
import com.ragtagger.brag.utils.Constants;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class NotificationHandlerActivity extends CoreActivity<NotificationHandlerActivity, ActivityNotificationHandlerBinding, NotificationHandlerViewModel> implements NotificationHandlerNavigator, HasSupportFragmentInjector {

    @Inject
    NotificationHandlerViewModel mNotificationHandlerViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityNotificationHandlerBinding mActivityNotificationHandlerBinding;

    private String mWhatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationHandlerViewModel.setNavigator(this);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if (intent.hasExtra(Constants.BUNDLE_KEY_NOTIFICATION_WHAT_ID)) {
                mWhatId = intent.getStringExtra(Constants.BUNDLE_KEY_NOTIFICATION_WHAT_ID);
                OrderDetailFragment fragment = OrderDetailFragment.newInstance(mWhatId);
                pushFragments(fragment, false, false);
            }
        }
    }

    @Override
    public NotificationHandlerViewModel getViewModel() {
        return mNotificationHandlerViewModel;
    }

    @Override
    public int getBindingVariable() {
        return com.ragtagger.brag.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification_handler;
    }

    @Override
    public void beforeLayoutSet() {

    }

    @Override
    public void afterLayoutSet() {
        mActivityNotificationHandlerBinding = getViewDataBinding();
    }

    @Override
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
