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
    String mCurrentUser;
    boolean isSalesRepresentative;

    public MyOrderItemViewModel(Context context, int position, DataMyOrder responeData, OnItemClick onItemClick, String currentUser/*Current user id*/, boolean isSaleseRepresentative/*is ucrrent user salse representative*/) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
        this.mCurrentUser = currentUser;
        this.isSalesRepresentative = isSaleseRepresentative;
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
        if (responeData.getCreateDateString() != null)
            //replacement added because of device specific issue, on mi devices it show in small caps
            return responeData.getCreateDateString().replace("am", "AM").replace("pm", "PM");
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

    public boolean isOrderedByVisible() {
        return (!isSalesRepresentative && !mCurrentUser.equals(responeData.getUser().getId()));
    }

    public String getOrderedBy() {
        return (responeData.getUser() != null && responeData.getUser().getFullName() != null) ? responeData.getUser().getFullName() : "";
    }

    public boolean isOnBehalfOfVisible() {
        return isSalesRepresentative && !mCurrentUser.equals(responeData.getOrderOnBehalfModel().getId());
    }

    public String getOnBehalfOf() {
        return (responeData.getOrderOnBehalfModel() != null && responeData.getOrderOnBehalfModel().getFullName() != null) ? responeData.getOrderOnBehalfModel().getFullName() : "";
    }
}
