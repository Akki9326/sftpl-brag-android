package com.ragtagger.brag.ui.notification.handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataNotification;
import com.ragtagger.brag.databinding.ActivityNotificationHandlerBinding;
import com.ragtagger.brag.ui.cart.CartFragment;
import com.ragtagger.brag.ui.home.product.details.ProductDetailFragment;
import com.ragtagger.brag.ui.order.orderdetail.OrderDetailFragment;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.ragtagger.brag.utils.Constants.BUNDLE_NOTIFICATION_MODEL;

public class NotificationHandlerActivity extends ToolbarActivity<NotificationHandlerActivity, ActivityNotificationHandlerBinding, NotificationHandlerViewModel> implements NotificationHandlerNavigator, HasSupportFragmentInjector {

    @Inject
    NotificationHandlerViewModel mNotificationHandlerViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityNotificationHandlerBinding mActivityNotificationHandlerBinding;

    private DataNotification mNotification;
    private String mTitle = "";

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
            if (intent.hasExtra(BUNDLE_NOTIFICATION_MODEL)) {
                mNotification = intent.getParcelableExtra(BUNDLE_NOTIFICATION_MODEL);
                //mWhatId = intent.getStringExtra(Constants.BUNDLE_KEY_NOTIFICATION_WHAT_ID);
                if (mNotification != null) {
                    switch (Constants.NotificationType.values()[mNotification.getNotificationType()]) {
                        case ORDER:
                            mTitle = getString(R.string.toolbar_label_order_detail);
                            OrderDetailFragment fragment = OrderDetailFragment.newInstance(mNotification.getContentId());
                            pushFragments(fragment, false, false);
                            break;
                        case ITEM:
                            mTitle = getString(R.string.toolbar_label_product_detail);
                            ProductDetailFragment fragmentDetails = ProductDetailFragment.newInstance(mNotification.getContentId(), true, true);
                            pushFragments(fragmentDetails, false, false);
                            break;
                    }

                    if (Utility.isConnection(getApplicationContext())) {
                        mNotificationHandlerViewModel.notificationRead(mNotification.getId());
                    }

                }
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
        super.afterLayoutSet();
        mActivityNotificationHandlerBinding = getViewDataBinding();
        Utility.applyTypeFace(getApplicationContext(), mActivityNotificationHandlerBinding.baseLayout);
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
        pushFragments(fragment, shouldAnimate, shouldAdd, null);
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd, String tag) {

        FragmentManager manager = getSupportFragmentManager();
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
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

    }

    @Override
    public void onApiSuccessNotificationRead() {
    }

    @Override
    public void onAPiErrorNotificationRead(ApiError error) {

    }

    @Override
    public void performCartClick() {
        super.performCartClick();
        pushFragments(new CartFragment(), true, true, "Cart");
    }

    @Override
    public void performBackClick() {
        super.performBackClick();
        Utility.hideSoftkeyboard(NotificationHandlerActivity.this);
        onBackPressed();
    }
}
