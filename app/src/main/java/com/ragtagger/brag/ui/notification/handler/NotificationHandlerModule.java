package com.ragtagger.brag.ui.notification.handler;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

@Module
public class NotificationHandlerModule {

    @Provides
    NotificationHandlerViewModel provideNotificationHandlerViewModel(IDataManager dataManager) {
        return new NotificationHandlerViewModel(dataManager);
    }
}
