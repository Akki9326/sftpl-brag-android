package com.pulse.brag.ui.myorder;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.view.View;

import com.pulse.brag.R;
import com.pulse.brag.pojo.datas.MyOrderListResponeData;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderItemViewModel extends BaseObservable {

    Context context;
    MyOrderListResponeData responeData;
    OnItemClick onItemClick;
    int position;

    public MyOrderItemViewModel(Context context, int position, MyOrderListResponeData responeData, OnItemClick onItemClick) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
    }

    public void setMyOrderData(MyOrderListResponeData responeData) {
        this.responeData = responeData;
        notifyChange();
    }

    public MyOrderListResponeData getMyOrderData() {
        return responeData;

    }

    public void onItemListClick(View view) {
        onItemClick.onItemClick(position, responeData);
    }

    public interface OnItemClick {
        void onItemClick(int position, MyOrderListResponeData responeData);

    }

    public String getId() {
        return responeData.getId();
    }

    public String getOrder_id() {
        return responeData.getOrder_id();
    }

    public String getProduct_name() {
        return responeData.getProduct_name();
    }

    public String getProduct_image_url() {
        return responeData.getProduct_image_url();
    }

    public String getProduct_qty() {
        return responeData.getProduct_qty();
    }

    public String getProduct_price() {
        return responeData.getProduct_price();
    }

    public String getProductPriceWithSym() {
        return Utility.getIndianCurrencePriceFormate(Integer.parseInt(responeData.getProduct_price()));
    }

    public String getStatusLable() {
        return Constants.OrderStatus.getOrderStatusLabel(context, responeData.getStatus());
    }

    public String getStatusLableWithDate() {
        return Constants.OrderStatus.getOrderStatusLabel(context, responeData.getStatus()) + " " + responeData.getDateFromTimestamp();
    }

    public int getStatus() {
        return responeData.getStatus();
    }

    public int getStatusColor() {
        if (responeData.getStatus() == Constants.OrderStatus.CANCALLED.ordinal())
            return R.color.order_status_red;
        else
            return R.color.order_status_green;
    }

}
