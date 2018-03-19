package com.ragtagger.brag.ui.notification;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

@Module
public class NotificationListModule {

    @Provides
    NotificationListViewModel provideNotificationViewModel(IDataManager dataManager) {
        return new NotificationListViewModel(dataManager);
    }
}
