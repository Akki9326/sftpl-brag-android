package com.ragtagger.brag.ui.home.product.details;

import android.text.Editable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataAddToCart;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public interface ProductDetailNavigator extends CoreNavigator {

    void performClickSizeGuide();

    void afterTextChanged(Editable s);

    boolean onEditorActionHide(TextView textView, int i, KeyEvent keyEvent);

    void performClickPlus();

    void performClickMinus();

    void onEditTextQty();

    void performAddToCartClick();

    void onAddedToCart(List<DataAddToCart> data);

    void updatePushCart();

    //void performClickNotifyMe();

    void onNotifyMeSuccess(String msg);

    void setProductDetails(DataProductList.Products products);

}
