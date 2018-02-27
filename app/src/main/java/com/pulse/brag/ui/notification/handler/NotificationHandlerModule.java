package com.pulse.brag.ui.notification.handler;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.splash.SplashViewModel;

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
