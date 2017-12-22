package com.pulse.brag.views.keyboardvisibilityevent;

import android.view.ViewTreeObserver;

/**
 * @author Anoop S S
 *         anoopvvs@gmail.com
 *         on 28/02/2017
 */

public interface Unregistrar {

    /**
     * unregisters the {@link ViewTreeObserver.OnGlobalLayoutListener} and there by does provide any more callback to the {@link KeyboardVisibilityEventListener}
     */
    void unregister();

}

