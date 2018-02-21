package com.pulse.brag.ui.myorder;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.response.MyOrderListRespone;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderViewModel extends CoreViewModel<MyOrderNavigator> {
    public MyOrderViewModel(IDataManager dataManager) {
        super(dataManager);

    }

    public void getOrderData() {
        Call<MyOrderListRespone> mOrderList = getDataManager().getOrderList("home/get/1");
        mOrderList.enqueue(new ApiResponse<MyOrderListRespone>() {
            @Override
            public void onSuccess(MyOrderListRespone myOrderListRespone, Headers headers) {
                if (myOrderListRespone.isStatus()) {
                    getNavigator().onApiSuccess();
                } else {
                    getNavigator().onApiError(new ApiError(myOrderListRespone.getErrorCode(), myOrderListRespone.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }
}
