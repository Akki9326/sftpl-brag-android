package com.ragtagger.brag.ui.notification;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

@Module
public abstract class NotificationListProvider {

    @ContributesAndroidInjector(modules = NotificationListModule.class)
    abstract NotificationListFragment provideNotificationListFragmentFactory();
}
