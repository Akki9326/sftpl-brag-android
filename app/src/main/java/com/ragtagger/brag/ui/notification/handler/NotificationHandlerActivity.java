package com.ragtagger.brag.ui.notification.handler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataNotification;
import com.ragtagger.brag.databinding.ActivityNotificationHandlerBinding;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.order.orderdetail.OrderDetailFragment;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.NotificationUtils;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.ragtagger.brag.utils.Constants.BUNDLE_NOTIFICATION_MODEL;

public class NotificationHandlerActivity extends CoreActivity<NotificationHandlerActivity, ActivityNotificationHandlerBinding, NotificationHandlerViewModel> implements NotificationHandlerNavigator, HasSupportFragmentInjector, CoreActivity.OnToolbarSetupListener {

    @Inject
    NotificationHandlerViewModel mNotificationHandlerViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityNotificationHandlerBinding mActivityNotificationHandlerBinding;

    private DataNotification mNotification;

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
                            OrderDetailFragment fragment = OrderDetailFragment.newInstance(mNotification.getContentId());
                            pushFragments(fragment, false, false);
                            break;
                        case ITEM:
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
        mActivityNotificationHandlerBinding = getViewDataBinding();
        // TODO: 16-02-2018 move to core activity
        if (bActivity instanceof OnToolbarSetupListener) {
            ((OnToolbarSetupListener) bActivity).setUpToolbar();
        }
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

    @Override
    public void setUpToolbar() {
        mNotificationHandlerViewModel.updateToolbarTitle(getString(R.string.toolbar_label_order_detail));
        mActivityNotificationHandlerBinding.toolbar.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideSoftkeyboard(NotificationHandlerActivity.this, view);
                onBackPressed();
            }
        });
    }

    @Override
    public void onApiSuccessNotificationRead() {

//        BragApp.NotificationNumber--;
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION));

    }

    @Override
    public void onAPiErrorNotificationRead(ApiError error) {

    }

    @Override
    public void onApiSuccessNotificationUnread() {

    }

    @Override
    public void onApiErrorNotificationUnread() {

    }
}
