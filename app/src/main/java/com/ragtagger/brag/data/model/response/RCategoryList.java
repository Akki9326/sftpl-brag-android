package com.ragtagger.brag.data.model.response;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataCategoryList;

/**
 * Created by nikhil.vadoliya on 16-10-2017.
 */


public class RCategoryList extends BaseResponse {
    private DataCategoryList data;
    public DataCategoryList getData() {
        return data;
    }

    public void setData(DataCategoryList data) {
        this.data = data;
    }


}
