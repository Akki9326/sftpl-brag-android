package com.pulse.brag.ui.order.orderdetail;


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
 * Created by nikhil.vadoliya on 14-03-2018.
 */


public class OrderCartItemViewMobel extends BaseObservable{

    Context context;
    CartData responeData;

    public OrderCartItemViewMobel(Context context, CartData responeData) {
        this.context = context;
        this.responeData = responeData;
    }


    public String getId() {
        return responeData.getId();
    }

    public String getProduct_image() {
        return responeData.getItem().getImages().get(0);
    }

    public String getProductName() {
        return responeData.getItem().getDescription();
    }

    public String getSize() {
        return responeData.getItem().getSizeCode();
    }

    public String getColor() {
        // TODO: 09-03-2018 color hex or color label
        return "#F44336";
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

        // TODO: 09-03-2018 color hex or label
        return new BitmapDrawable(context.getResources(), Utility.getRoundBitmap("#F44336", true));
//        return new BitmapDrawable(context.getResources(), Utility.getRoundBitmap(responeData.getItem().getColorCode(), true));


    }

}
