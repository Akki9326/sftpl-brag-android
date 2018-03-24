package com.ragtagger.brag.ui.home.product.details;

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataAddToCart;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.ui.core.CoreNavigator;

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

    void onApiSuccessProductDetail(DataProductList.Products products);

    void onApiErrorProductDetail(ApiError error);
}
