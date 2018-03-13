package com.pulse.brag.ui.order;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.pulse.brag.R;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.response.RMyOrder;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.response.RMyOrderList;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderViewModel extends CoreViewModel<MyOrderNavigator> {

    private final ObservableField<Boolean> visibility = new ObservableField<>();
    ObservableField<Boolean> noInternet = new ObservableField<>();

    public MyOrderViewModel(IDataManager dataManager) {
        super(dataManager);

    }

    public void getOrderData(int page) {
        Call<RMyOrder> mOrderList = getDataManager().getOrderList(page);
        mOrderList.enqueue(new ApiResponse<RMyOrder>() {
            @Override
            public void onSuccess(RMyOrder myOrderListRespone, Headers headers) {
                if (myOrderListRespone.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().getOrderList(myOrderListRespone.getData(),myOrderListRespone.getData().getObjects());
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

    public ObservableField<Boolean> getListVisibility() {
        return visibility;
    }

    public void setListVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNavigator().swipeRefresh();
            }
        };
    }

    public int[] getColorSchemeResources() {
        return new int[]{
                R.color.pink,
        };
    }

    public ObservableField<Boolean> getNoInternet() {
        return noInternet;
    }

    public void setNoInternet(boolean noInternet) {
        this.noInternet.set(noInternet);
    }

}
