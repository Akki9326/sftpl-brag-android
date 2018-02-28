package com.pulse.brag.ui.notification.handler;

import android.os.Bundle;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.ActivityNotificationHandlerBinding;
import com.pulse.brag.ui.core.CoreActivity;

import javax.inject.Inject;

public class NotificationHandlerActivity extends CoreActivity<NotificationHandlerActivity, ActivityNotificationHandlerBinding, NotificationHandlerViewModel> implements NotificationHandlerNavigator {

    @Inject
    NotificationHandlerViewModel mNotificationHandlerViewModel;

    ActivityNotificationHandlerBinding mActivityNotificationHandlerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_handler);
        mNotificationHandlerViewModel.setNavigator(this);
    }

    @Override
    public NotificationHandlerViewModel getViewModel() {
        return mNotificationHandlerViewModel;
    }

    @Override
    public int getBindingVariable() {
        return com.pulse.brag.BR.viewModel;
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
}
