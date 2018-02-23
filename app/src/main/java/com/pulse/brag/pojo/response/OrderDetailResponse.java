package com.pulse.brag.pojo.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.pojo.datas.MyOrderListResponeData;
import com.pulse.brag.pojo.datas.OrderDetailResponeData;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 23-02-2018.
 */


public class OrderDetailResponse {

    private boolean status;
    private Integer errorCode;
    private String message;
    private OrderDetailResponeData data;

    public OrderDetailResponeData getData() {
        return data;
    }

    public void setData(OrderDetailResponeData data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
