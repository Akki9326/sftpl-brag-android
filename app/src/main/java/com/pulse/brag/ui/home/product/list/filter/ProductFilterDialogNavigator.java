package com.pulse.brag.ui.home.product.list.filter;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public interface ProductFilterDialogNavigator extends CoreNavigator {

    void dismissFragment();
    void applyFilter();
    void resetFilter();
}
