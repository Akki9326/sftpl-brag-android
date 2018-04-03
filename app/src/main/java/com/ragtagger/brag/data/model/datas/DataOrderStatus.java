package com.ragtagger.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;

import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.DateFormatter;

/**
 * Created by nikhil.vadoliya on 02-04-2018.
 */


public class DataOrderStatus {

    private int statusTo;
    private Long createdDate;
    private int orderStatusStepper;

    public DataOrderStatus(int statusTo, Long createdDate, int orderStatus) {
        this.statusTo = statusTo;
        this.createdDate = createdDate;
        this.orderStatusStepper = orderStatus;
    }

    public int getStatusTo() {
        return statusTo;
    }

    public void setStatusTo(int statusTo) {
        this.statusTo = statusTo;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatusLabel(Context context) {
        return Constants.OrderStatus.getOrderStatusLabel(context, statusTo);
    }

    public String getStatusDate() {
        if (getCreatedDate() != null) {
            return DateFormatter.convertMillisToStringDate(getCreatedDate(), DateFormatter.dd_MMM_YYYY_HH_MM_A);
        } else {
            return "";
        }
    }

    public int getOrderStatesColor() {
        if (getStatusTo() == Constants.OrderStatus.CANCELED.ordinal()
                || getStatusTo() == Constants.OrderStatus.REJECTED.ordinal()) {
            return R.color.order_status_red;
        } else if (getStatusTo() == Constants.OrderStatus.PLACED.ordinal()) {
            return R.color.order_status_yellow;
        } else {
            return R.color.order_status_green;
        }
    }

    public int getOrderStatusStepper() {
        return orderStatusStepper;
    }

    public void setOrderStatusStepper(int orderStatus) {
        this.orderStatusStepper = orderStatus;
    }
}
