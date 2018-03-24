package com.ragtagger.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public interface SubCategoryNavigator extends CoreNavigator {

    void onApiSuccess();

    void onApiError(ApiError error);

    void swipeRefresh();

    void setCategoryList(List<DataCategoryList.Category> list);

    void onNoData();
}