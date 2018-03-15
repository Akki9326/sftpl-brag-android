package com.pulse.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public interface OrderDetailNavigator extends CoreNavigator {

    void onApiReorderSuccess();

    void onApiReorderError(ApiError error);

    void onDownloadInvoice();

    void onReorderClick();
}
