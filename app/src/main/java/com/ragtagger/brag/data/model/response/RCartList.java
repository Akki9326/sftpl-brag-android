package com.ragtagger.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataCart;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 04-12-2017.
 */


public class RCartList extends BaseResponse {
    private List<DataCart> data;

    public List<DataCart> getData() {
        return data;
    }

    public void setData(List<DataCart> data) {
        this.data = data;
    }
}
