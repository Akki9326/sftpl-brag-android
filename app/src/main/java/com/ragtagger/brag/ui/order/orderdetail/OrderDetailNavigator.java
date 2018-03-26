package com.ragtagger.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  YsetDeviceTokenou shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public interface OrderDetailNavigator extends CoreNavigator {

    void onApiReorderSuccess();

    void onApiReorderError(ApiError error);

    void onDownloadInvoice();

    void onApiSuccessDownload();

    void onApiErrorDownload(ApiError error);

    void onReorderClick();

    void onOrderDetails(DataMyOrder orderDetails);

    void onNoOrderData();

    void onCancelledClick();

    void onApiCancelSuccess();

    void onApiCancelError(ApiError error);
}
