package com.pulse.brag.core;

import android.arch.lifecycle.ViewModel;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreViewModel<N> extends ViewModel {

    private N mNavigator;

    public CoreViewModel() {
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
}
