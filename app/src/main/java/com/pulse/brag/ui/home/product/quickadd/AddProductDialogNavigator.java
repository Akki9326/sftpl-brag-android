package com.pulse.brag.ui.home.product.quickadd;

import android.text.Editable;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/20/2018.
 */

public interface AddProductDialogNavigator extends CoreNavigator {

    void dismissFragment();
    void editQty();
    void addToCart();
    void afterTextChanged(Editable s);
    void notifyMe();

}
