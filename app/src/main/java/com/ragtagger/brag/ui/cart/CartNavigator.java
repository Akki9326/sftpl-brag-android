package com.ragtagger.brag.ui.cart;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataCart;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public interface CartNavigator {

    void onApiSuccess();

    void onApiError(ApiError error);

    void onContinuesClick();

    void onPriceClick();

    void swipeRefresh();

    void getCartList(List<DataCart> list);

    void onSuccessDeleteFromAPI();

    void onErrorDeleteFromAPI(ApiError error);

    void onSuccessEditQtyFromAPI();

    void onErrorEditQtyFromAPI(ApiError error);
}
