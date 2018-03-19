package com.ragtagger.brag.di.component;

import android.app.Application;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.di.builder.ActivityBuilder;
import com.ragtagger.brag.di.module.AppModule;
import com.ragtagger.brag.fcm.FCMService;
import com.ragtagger.brag.fcm.RegistrationTokenService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(BragApp app);

    void inject(RegistrationTokenService registrationTokenService);

    void inject(FCMService fcmService);
}
