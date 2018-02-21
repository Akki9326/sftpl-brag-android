package com.pulse.brag.ui.home.category;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.view.View;

import com.pulse.brag.data.model.ApiError;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public interface CategoryNavigator {

    void onApiSuccess();
    void onApiError(ApiError error);

}
