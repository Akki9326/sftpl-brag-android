package com.ragtagger.brag.ui.notification;

import com.ragtagger.brag.data.model.datas.DataNotificationList;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public interface NotificationListNavigator extends CoreNavigator {

    void readNotification();

    void onNoData();

    void setData(DataNotificationList data);

    void setProductList(int notificationSize, List<DataNotificationList.Notification> notificationList);
}
