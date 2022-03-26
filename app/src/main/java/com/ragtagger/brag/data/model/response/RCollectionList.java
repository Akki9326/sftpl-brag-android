package com.ragtagger.brag.data.model.response;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataCollectionList;

/**
 * Created by nikhil.vadoliya on 13-12-2017.
 */


public class RCollectionList extends BaseResponse {
    private DataCollectionList data;

    public DataCollectionList getData() {
        return data;
    }

    public void setData(DataCollectionList data) {
        this.data = data;
    }
}
