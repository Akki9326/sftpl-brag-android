package com.ragtagger.brag.ui.cart;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.downloader.core.Core;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataCart;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public interface CartNavigator extends CoreNavigator {

    void performClickPrice();

    void performClickContinues();

    void performSwipeRefresh();

    void setCartList(List<DataCart> list);

    void onSuccessDeleteFromAPI();

    void onErrorDeleteFromAPI(ApiError error);

    void onSuccessEditQtyFromAPI();

    void onErrorEditQtyFromAPI(ApiError error);
}
