package com.ragtagger.brag.ui.order;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.data.model.response.RMyOrderList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public interface MyOrderNavigator extends CoreNavigator {

    void performSwipeRefresh();

    void setOrderList(RMyOrderList orderList, List<DataMyOrder> listRespones);
}
