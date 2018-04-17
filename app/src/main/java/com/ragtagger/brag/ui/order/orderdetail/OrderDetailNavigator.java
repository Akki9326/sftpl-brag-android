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

    void setOrderDetails(DataMyOrder orderDetails);

    void noData();

    void performClickStatus();

    void performClickCancel();

    void onCancelledSuccessfully();

    void performClickDownloadInvoice();

    void onDownloadInvoiceSuccessfully();

    void performClickReorder();

    void onReorderSuccessfully();

}
