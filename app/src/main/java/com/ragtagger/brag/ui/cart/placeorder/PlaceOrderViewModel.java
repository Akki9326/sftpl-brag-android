package com.ragtagger.brag.ui.cart.placeorder;


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
import com.ragtagger.brag.data.model.datas.DataUserAddress;
import com.ragtagger.brag.data.model.requests.QPlaceOrder;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class PlaceOrderViewModel extends CoreViewModel<PlaceOrderNavigator> {

    ObservableField<String> total = new ObservableField<>();
    ObservableField<String> listSize = new ObservableField<>();
    ObservableField<String> address = new ObservableField<>();
    ObservableField<Boolean> isAddressAvaliable = new ObservableField<>();
    String itemsLable;
    DataUserAddress userAddress;

    public PlaceOrderViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public ObservableField<String> getTotalPrice() {
        return total;
    }

    public String getFullName() {
        return getDataManager().getUserData().getFullName();
    }

    public String getMobileNum() {
        return getDataManager().getUserData().getMobileNumber();
    }

    public void setListSize(int listSize) {
        this.listSize.set(String.valueOf(listSize));
        if (listSize > 1) {
            itemsLable = "items";
        } else {
            itemsLable = "item";
        }
    }

    public ObservableField<String> getListSize() {
        return listSize;
    }

    public String getItemsLable() {
        return itemsLable;
    }

    public View.OnClickListener onPlaceOrder() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onPlaceOrder();
            }
        };
    }

    public View.OnClickListener onEditAddress() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onEditAddress();
            }
        };
    }

    public View.OnClickListener onAddAddress() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onAddAddress();
            }
        };
    }

    public View.OnClickListener onPriceLabelClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onPriceLabelClick();
            }
        };
    }

    public void getUserProfile() {
        Call<RLogin> responseCall = getDataManager().getUserProfile("user/getProfile");
        responseCall.enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin loginResponse, Headers headers) {
                if (loginResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    setAddress(loginResponse.getData().getFullAddressWithNewLine());
                    if (loginResponse.getData().getAddresses() == null || loginResponse.getData().getAddresses().isEmpty()) {
                        setIsAddressAvaliable(false);
                    } else {
                        setIsAddressAvaliable(true);
                        setUserAddress(loginResponse.getData().getAddresses().get(0));
                    }
                } else {
                    getNavigator().onApiError(new ApiError(loginResponse.getErrorCode(), loginResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(new ApiError(t.getHttpCode(), t.getMessage()));
            }
        });
    }

    public ObservableField<String> getFullAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.isEmpty())
            setIsAddressAvaliable(true);
        this.address.set(address);
    }

    public void placeOrderAPI(QPlaceOrder placeOrder) {
        Call<RGeneralData> responseCall = getDataManager().placeOrder(placeOrder);
        responseCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccessPlaceOrder();
                } else {
                    getNavigator().onApiErrorPlaceOrder(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiErrorPlaceOrder(t);
            }
        });
    }

    public ObservableField<Boolean> IsAddressAvaliable() {
        return isAddressAvaliable;
    }

    public void setIsAddressAvaliable(boolean isAddressAvaliable) {
        this.isAddressAvaliable.set(isAddressAvaliable);
    }

    public DataUserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(DataUserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
