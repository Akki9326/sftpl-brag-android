package com.ragtagger.brag.views.webview;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

@Module
public class WebviewDialogModule {

    @Provides
    WebviewDialogViewModel provideWebviewDialogViewModel(IDataManager dataManager) {
        return new WebviewDialogViewModel(dataManager);
    }
}
