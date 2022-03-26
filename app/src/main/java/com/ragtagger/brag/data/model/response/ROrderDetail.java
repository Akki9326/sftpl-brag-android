package com.ragtagger.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataMyOrder;

/**
 * Created by nikhil.vadoliya on 23-02-2018.
 */


public class ROrderDetail extends BaseResponse {

    private DataMyOrder data;

    public DataMyOrder getData() {
        return data;
    }

    public void setData(DataMyOrder data) {
        this.data = data;
    }
}
