package com.pulse.brag.callback;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public interface IFragmentCallback {

    void onFragmentAttached();

    void onFragmentDetached(String tag);
}
