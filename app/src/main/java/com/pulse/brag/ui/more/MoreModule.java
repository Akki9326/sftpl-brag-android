package com.pulse.brag.ui.more;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.myorder.MyOrderViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/26/2018.
 */

@Module
public class MoreModule {

    @Provides
    MoreViewModel provideMoreViewModel(IDataManager dataManager) {
        return new MoreViewModel(dataManager);
    }
}
