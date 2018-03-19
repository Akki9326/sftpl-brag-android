package com.ragtagger.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public class OrderDetailViewModel extends CoreViewModel<OrderDetailNavigator> {


    ObservableField<String> orderId = new ObservableField<>();
    ObservableField<String> address = new ObservableField<>();
    ObservableField<String> fullName = new ObservableField<>();
    ObservableField<String> orderState = new ObservableField<>();
    ObservableField<String> date = new ObservableField<>();
    ObservableField<Boolean> isOrderApprove = new ObservableField<>();
    ObservableField<Integer> orderStateColor = new ObservableField<>();
    ObservableField<String> total = new ObservableField<>();
    ObservableField<Integer> listSize = new ObservableField<>();
    String itemsLable;

    public OrderDetailViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getOrderId() {
        return orderId;
    }

    public void updateOrderId(String orderId) {
        this.orderId.set(orderId);
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public void updateAddress(String address) {
        this.address.set(address);
    }


    public View.OnClickListener onDownloadInvoice() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onDownloadInvoice();
            }
        };
    }

    public View.OnClickListener onReorder() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onReorderClick();
            }
        };
    }

    public String getInvoiveUrl() {
        return "https://www.tutorialspoint.com/android/android_tutorial.pdf";
    }

    public void updateFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public ObservableField<String> getFullName() {
        return fullName;
    }


    public void updateIsOrderApprove(boolean isApprove) {
        this.isOrderApprove.set(isApprove);
    }

    public ObservableField<Boolean> isApprvoe() {
        return isOrderApprove;
    }

    public void reOrderAPI(String orderId) {
        Call<RGeneralData> call = getDataManager().reOrder(orderId);
        call.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiReorderSuccess();
                } else {
                    getNavigator().onApiReorderError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiReorderError(t);
            }
        });
    }

    public void updateOrderState(String orderState) {
        this.orderState.set(orderState);
    }

    public ObservableField<String> getOrderState() {
        return orderState;
    }

    public void updateOrderStateDate(String date) {
        this.date.set(date);
    }

    public ObservableField<String> getOrderStateDate() {
        return date;
    }

    public void updateTotalCartNum(int size) {
        listSize.set(size);
        if (size > 1) {
            itemsLable = "items";
        } else {
            itemsLable = "item";
        }
    }

    public ObservableField<Integer> getCartSize() {
        return listSize;
    }

    public String getItemsLable() {
        return itemsLable;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public ObservableField<String> getTotalPrice() {
        return total;
    }
}
