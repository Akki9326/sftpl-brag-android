package com.ragtagger.brag.ui.notification.handler;

import android.os.Bundle;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.ActivityNotificationHandlerBinding;
import com.ragtagger.brag.ui.core.CoreActivity;

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
}
