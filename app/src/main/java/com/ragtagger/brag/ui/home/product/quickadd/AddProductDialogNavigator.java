package com.ragtagger.brag.ui.home.product.quickadd;

import android.text.Editable;

import com.ragtagger.brag.data.model.datas.DataAddToCart;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/20/2018.
 */

public interface AddProductDialogNavigator extends CoreNavigator {

    void dismissFragment();

    void editQty();

    void addToCart();

    void onAddedToCart(List<DataAddToCart> data);

    void afterTextChanged(Editable s);

    void notifyMe();

    void onNotifyMeSuccess(String msg);

}
