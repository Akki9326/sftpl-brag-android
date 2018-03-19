package com.ragtagger.brag.callback;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataProductList;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-10-2017.
 */


public interface IOnProductColorSelectListener {
    void onSelectedColor(int prevPos, int pos, List<DataProductList.Size> sizes);
}
