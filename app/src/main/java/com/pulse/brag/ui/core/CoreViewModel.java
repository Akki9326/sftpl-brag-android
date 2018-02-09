package com.pulse.brag.ui.core;

import android.arch.lifecycle.ViewModel;

import com.pulse.brag.data.IDataManager;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreViewModel<N> extends ViewModel {

    private N mNavigator;
    private final IDataManager mDataManager;

    public CoreViewModel(IDataManager dataManager) {
        this.mDataManager=dataManager;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N mNavigator) {
        this.mNavigator = mNavigator;
    }

    public IDataManager getDataManager() {
        return mDataManager;
    }
}
