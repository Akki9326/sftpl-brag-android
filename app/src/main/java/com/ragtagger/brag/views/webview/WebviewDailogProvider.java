package com.ragtagger.brag.views.webview;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

@Module
public abstract class WebviewDailogProvider {

    @ContributesAndroidInjector(modules = WebviewDialogModule.class)
    abstract WebviewDialogFragment provideWebviewDialogFragmentFactory();
}
