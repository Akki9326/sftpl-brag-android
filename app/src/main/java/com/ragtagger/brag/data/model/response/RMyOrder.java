package com.ragtagger.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 13-03-2018.
 */


public class RMyOrder extends BaseResponse {
    private RMyOrderList data;
    public RMyOrderList getData() {
        return data;
    }

    public void setData(RMyOrderList list) {
        this.data = list;
    }
}
