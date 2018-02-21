package com.pulse.brag.ui.cart;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.ApiError;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public interface CartNavigator {

    void onApiSuccess();

    void onApiError(ApiError error);

    void onPlaceOrderClick();

    void onPriceClick();
}
