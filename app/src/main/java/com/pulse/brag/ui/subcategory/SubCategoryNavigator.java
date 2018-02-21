package com.pulse.brag.ui.subcategory;


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
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public interface SubCategoryNavigator extends CoreNavigator {

    void onApiSuccess();
    void onApiError(ApiError error);
}