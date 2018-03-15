package com.pulse.brag.ui.order;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataMyOrder;
import com.pulse.brag.data.model.response.RMyOrderList;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public interface MyOrderNavigator {

    void onApiSuccess();

    void onApiError(ApiError error);

    void swipeRefresh();

    void getOrderList(RMyOrderList orderList, List<DataMyOrder> listRespones);
}
