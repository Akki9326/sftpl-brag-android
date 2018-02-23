package com.pulse.brag.ui.myorder.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.datas.OrderDetailResponeData;
import com.pulse.brag.pojo.response.OrderDetailResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public class OrderDetailViewModel extends CoreViewModel<OrderDetailNavigator> {


    ObservableField<String> productName = new ObservableField<>();
    ObservableField<String> orderId = new ObservableField<>();
    ObservableField<String> productImageUrl = new ObservableField<>();
    ObservableField<String> productQty = new ObservableField<>();
    ObservableField<String> productPrice = new ObservableField<>();
    ObservableField<String> address = new ObservableField<>();
    ObservableField<String> city = new ObservableField<>();
    ObservableField<String> state = new ObservableField<>();
    ObservableField<String> pincode = new ObservableField<>();
    ObservableField<String> stateWithPincode = new ObservableField<>();

    public OrderDetailViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void getOrderDetail() {
        final Call<OrderDetailResponse> orderDetail = getDataManager().getOrderDetail("home/get/1");
        orderDetail.enqueue(new ApiResponse<OrderDetailResponse>() {
            @Override
            public void onSuccess(OrderDetailResponse orderDetailResponse, Headers headers) {
                if (orderDetailResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    OrderDetailResponeData mData = orderDetailResponse.getData();
                    updateProductName(mData.getProduct_name());
                    updateOrderId(mData.getOrder_id());
                    updateProductImage(mData.getProduct_image_url());
                    updateProductPrice(mData.getProduct_price());
                    updateProductQty(mData.getProductPriceWithSym());
                } else {
                    getNavigator().onApiError(new ApiError(orderDetailResponse.getErrorCode(), orderDetailResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public ObservableField<String> getProductName() {
        return productName;
    }

    public void updateProductName(String product) {
        productName.set(product);
    }

    public ObservableField<String> getOrderId() {
        return orderId;
    }

    public void updateOrderId(String orderId) {
        this.orderId.set(orderId);
    }

    public ObservableField<String> getProductqty() {
        return productQty;
    }

    public void updateProductQty(String qty) {
        productQty.set(qty);
    }

    public ObservableField<String> getProductPrice() {
        return productPrice;
    }

    public void updateProductPrice(String price) {
        productPrice.set(price);
    }

    public ObservableField<String> getProductImage() {
        return productImageUrl;
    }

    public void updateProductImage(String imageUrl) {
        productImageUrl.set(imageUrl);
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public void updateAddress(String address) {
        this.address.set(address);
    }

    public ObservableField<String> getCity() {
        return city;
    }

    public void updateCity(String city) {
        this.city.set(city);
    }

    public ObservableField<String> getState() {
        return state;
    }

    public void updateState(String state) {
        this.state.set(state);
    }

    public ObservableField<String> getPincode() {
        return pincode;
    }

    public void updatePincode(String pincode) {
        this.pincode.set(pincode);
    }

    public ObservableField<String> getStateWithPincode() {
        return stateWithPincode;
    }

    public void updateStateWithPincode(String statewithpincode) {
        this.stateWithPincode.set(statewithpincode);
    }

    public String getFullName() {
        return getDataManager().getUserData().getFullName();
    }
}
