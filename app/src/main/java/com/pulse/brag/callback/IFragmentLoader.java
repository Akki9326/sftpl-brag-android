package com.pulse.brag.callback;

import android.support.v4.app.Fragment;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public interface IFragmentLoader {

    void pushFragment(int containerId, Fragment fragment, boolean addToStack, String tag);

    void pushFragment(int containerId, Fragment fragment, boolean addToStack);

    void pushFragment(int containerId, Fragment fragment, String tag);

    void pushFragment(int containerId, Fragment fragment);
}
