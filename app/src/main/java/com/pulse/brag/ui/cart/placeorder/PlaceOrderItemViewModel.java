package com.pulse.brag.ui.cart.placeorder;


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

import com.pulse.brag.data.model.datas.CartData;
import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class PlaceOrderItemViewModel extends BaseObservable {


    Context context;
    CartData responeData;
    OnItemClick onItemClick;
    int position;

    public PlaceOrderItemViewModel(Context context, int position, CartData responeData, OnItemClick onItemClick) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
    }

    public void setPlaceOrderData(CartData responeData) {
        this.responeData = responeData;
        notifyChange();
    }

    public String getId() {
        return responeData.getId();
    }

    public String getProduct_image() {
        return responeData.getItem().getImages().get(0);
    }

    public String getProduct_name() {
        return responeData.getItem().getDescription();
    }

    public String getSize() {
        return responeData.getItem().getSizeCode();
    }

    public String getColor() {

        return "#F44336";
        // TODO: 09-03-2018 color or hex
//        return responeData.getColor();
    }

    public int getPrice() {
        return responeData.getItem().getUnitPrice();
    }

    public String getQty() {
        return String.valueOf(responeData.getQuantity());
    }

    public String getPriceWithSym() {
        return Utility.getIndianCurrencyPriceFormat(getPrice());
    }

    public Drawable getColorBitMap() {

        // TODO: 09-03-2018 color or hex
        return new BitmapDrawable(context.getResources(), Utility.getRoundBitmap("#F44336",true));
//        return new BitmapDrawable(context.getResources(), Utility.getRoundBitmap(responeData.getColor(),true));


    }
    public void onQtyClick(View view) {
        onItemClick.onQtyClick(position, responeData);
    }

    public interface OnItemClick {
        void onQtyClick(int position, CartData responeData);
    }
}
