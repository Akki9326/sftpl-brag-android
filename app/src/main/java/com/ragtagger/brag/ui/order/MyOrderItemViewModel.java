package com.ragtagger.brag.ui.order;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderItemViewModel extends BaseObservable {

    Context context;
    DataMyOrder responeData;
    OnItemClick onItemClick;
    int position;

    public MyOrderItemViewModel(Context context, int position, DataMyOrder responeData, OnItemClick onItemClick) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
    }


    public void onItemListClick(View view) {
        onItemClick.onItemClick(position, responeData);
    }

    public interface OnItemClick {
        void onItemClick(int position, DataMyOrder responeData);

    }

    public String getId() {
        return responeData.getId();
    }

    public String getOrder_id() {
        return responeData.getOrderNumber();
    }

    public String getProduct_name() {
        return "Product Name";
    }

    public String getProduct_image_url() {
        return "";
    }

    public String getProduct_qty() {
        return "";
    }

    public String getProduct_price() {
        return "";
    }

    public String getProductPriceWithSym() {
        return Utility.getIndianCurrencyPriceFormat((responeData.getStatus() == Constants.OrderStatus.APPROVED.ordinal() || responeData.getStatus() == Constants.OrderStatus.DISPATCHED.ordinal() || responeData.getStatus() == Constants.OrderStatus.DELIVERED.ordinal()) ? responeData.getPayableAmount() : responeData.getTotalAmount());

    }

    public String getStatusLable() {
        return Constants.OrderStatus.getOrderStatusLabel(context, responeData.getStatus());
    }

    public Long getDate() {
        return responeData.getCreatedDate();
    }

    public String getCreateDateString() {
        return responeData.getCreateDateString();
    }

    public String getStatusLableWithDate() {
        return Constants.OrderStatus.getOrderStatusLabel(context, responeData.getStatus()) + " - "
                + responeData.getCreateDateString();
    }

    public int getStatus() {
        return responeData.getStatus();
    }

    public int getStatusColor() {
        return responeData.getOrderStatesColor(context);
    }

    public boolean hasOrderedBy() {
        return responeData.getOrderedBy() != null;
    }

    public String getOrderedBy() {
        return responeData.getOrderedBy();
    }
}
