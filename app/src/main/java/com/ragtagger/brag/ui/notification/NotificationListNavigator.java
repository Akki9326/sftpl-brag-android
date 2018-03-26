package com.ragtagger.brag.ui.notification;

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataNotificationList;
import com.ragtagger.brag.data.model.response.RNotificationList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.Date;
import java.util.List;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public interface NotificationListNavigator extends CoreNavigator {


    void setNotificationList(RNotificationList notificationList, List<DataNotificationList> lists);

    void swipeToRefresh();

    void onApiSuccessNotificationRead();

    void onAPiErrorNotificationRead(ApiError error);
}
