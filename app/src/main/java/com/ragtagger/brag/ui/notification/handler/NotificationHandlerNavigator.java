package com.ragtagger.brag.ui.notification.handler;

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public interface NotificationHandlerNavigator extends CoreNavigator {
    void onApiSuccessNotificationRead();

    void onAPiErrorNotificationRead(ApiError error);

    void onApiSuccessNotificationUnread();

    void onApiErrorNotificationUnread();
}
