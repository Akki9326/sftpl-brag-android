package com.pulse.brag.ui.home.product.details;

import com.pulse.brag.data.model.datas.DataAddToCart;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public interface ProductDetailNavigator extends CoreNavigator {

    void sizeGuide();

    void plus();

    void minus();

    void addToCart();

    void onAddedToCart(List<DataAddToCart> data);

    void notifyMe();

    void onNotifyMeSuccess(String msg);
}
