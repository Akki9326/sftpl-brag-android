package com.pulse.brag.ui.cart;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.pulse.brag.data.model.datas.CartListResponeData;
import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public class CartItemViewModel extends BaseObservable {

    Context context;
    CartListResponeData responeData;
    OnItemClick onItemClick;
    int position;

    public CartItemViewModel(Context context, int position, CartListResponeData responeData, OnItemClick onItemClick) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
    }

    public void setCartData(CartListResponeData responeData) {
        this.responeData = responeData;
        notifyChange();
    }

    public String getId() {
        return responeData.getId();
    }

    public String getProduct_image() {
        return responeData.getProduct_image();
    }

    public String getProduct_name() {
        return responeData.getProduct_name();
    }

    public String getSize() {
        return responeData.getSize();
    }

    public String getColor() {
        return responeData.getColor();
    }

    public int getPrice() {
        return responeData.getPrice();
    }

    public String getQty() {
        return String.valueOf(responeData.getQty());
    }

    public String getPriceWithSym() {
        return Utility.getIndianCurrencePriceFormate(getPrice());
    }

    public Drawable getColorBitMap() {
        return new BitmapDrawable(context.getResources(), Utility.getRoundBitmap(responeData.getColor(), true));


    }

    public void onDeleteItem(View v) {
        onItemClick.onDeleteItem(position, responeData);
    }

    public void onQtyClick(View view) {
        onItemClick.onQtyClick(position, responeData);
    }

    public interface OnItemClick {
        public void onDeleteItem(int position, CartListResponeData responeData);
        public void onQtyClick(int position, CartListResponeData responeData);
    }
}
