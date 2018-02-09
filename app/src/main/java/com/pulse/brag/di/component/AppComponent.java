package com.pulse.brag.di.component;

import android.app.Application;

import com.pulse.brag.BragApp;
import com.pulse.brag.di.builder.ActivityBuilder;
import com.pulse.brag.di.module.AppModule;

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
}
