package com.pulse.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.datas.OrderDetailResponeData;
import com.pulse.brag.data.model.response.OrderDetailResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public class OrderDetailViewModel extends CoreViewModel<OrderDetailNavigator> {


    ObservableField<String> orderId = new ObservableField<>();
    ObservableField<String> address = new ObservableField<>();
    ObservableField<String> fullName = new ObservableField<>();
    ObservableField<Boolean> isOrderApprove = new ObservableField<>();

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
}
