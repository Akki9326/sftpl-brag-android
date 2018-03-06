package com.pulse.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.CategoryListResponseData;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public interface SubCategoryNavigator extends CoreNavigator {

    void onApiSuccess();

    void onApiError(ApiError error);

    void swipeRefresh();

    void setCategoryList(List<CategoryListResponseData.CategoryList> list);

    void onNoData();
}
