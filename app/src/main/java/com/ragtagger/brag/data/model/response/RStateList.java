package com.ragtagger.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataState;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 09-03-2018.
 */


public class RStateList extends BaseResponse {
    private List<DataState> data;

    public List<DataState> getData() {
        return data;
    }

    public void setData(List<DataState> data) {
        this.data = data;
    }
}
