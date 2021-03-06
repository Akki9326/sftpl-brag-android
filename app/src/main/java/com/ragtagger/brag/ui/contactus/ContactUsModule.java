package com.ragtagger.brag.ui.contactus;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public class ContactUsModule {

    @Provides
    ContactUsViewModel provideContactUsViewModelViewModel(IDataManager dataManager){
        return new ContactUsViewModel(dataManager);
    }
}
