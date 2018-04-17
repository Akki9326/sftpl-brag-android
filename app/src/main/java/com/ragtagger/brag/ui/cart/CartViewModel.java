package com.ragtagger.brag.ui.cart;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.requests.QAddToCart;
import com.ragtagger.brag.data.model.response.RAddToCart;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RCartList;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Constants;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public class CartViewModel extends CoreViewModel<CartNavigator> {

    ObservableField<String> total = new ObservableField<>();
    ObservableField<Integer> listNum = new ObservableField<>();
    ObservableField<Boolean> visibility = new ObservableField<>();
    ObservableField<Boolean> noInternet = new ObservableField<>();

    public CartViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getTotalPrice() {
        return total;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public ObservableField<Boolean> getListVisibility() {
        return visibility;
    }

    public void setListVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public void setListNum(int count) {
        this.listNum.set(count);
    }

    public ObservableField<Boolean> getNoInternet() {
        return noInternet;
    }

    public void setNoInternet(boolean noInternet) {
        this.noInternet.set(noInternet);
    }

    public View.OnClickListener onContinuesClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickContinues();
            }
        };
    }

    public View.OnClickListener onPriceClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickPrice();
            }
        };
    }

    public SwipeRefreshLayout.OnRefreshListener setOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNavigator().performSwipeRefresh();
            }
        };
    }

    public int[] getColorSchemeResources() {
        return new int[]{
                R.color.pink,
        };
    }

    void callGetCartDataApi() {
        Call<RCartList> cartListResponseCall = getDataManager().getCartList("item/getCart");
        cartListResponseCall.enqueue(new ApiResponse<RCartList>() {
            @Override
            public void onSuccess(RCartList cartListResponse, Headers headers) {
                if (cartListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().setCartList(cartListResponse.getData());
                } else {
                    getNavigator().onApiError(new ApiError(cartListResponse.getErrorCode(), cartListResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    void callRemoveFromCartApi(String itemId) {
        Call<RGeneralData> cartListResponseCall = getDataManager().removeFromCart("item/removeFromCart/" + itemId);
        cartListResponseCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData cartListResponse, Headers headers) {
                if (cartListResponse.isStatus()) {
                    BragApp.CartNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_CART_NUM));
                    getNavigator().onSuccessDeleteFromAPI();

                } else {
                    getNavigator().onErrorDeleteFromAPI(new ApiError(cartListResponse.getErrorCode(), cartListResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onErrorDeleteFromAPI(t);
            }
        });
    }

    void callEditQtyApi(String id, String itemId, int qty) {
        Call<RAddToCart> callAddToCart = getDataManager().addToCart(new QAddToCart(id, itemId, qty));
        callAddToCart.enqueue(new ApiResponse<RAddToCart>() {
            @Override
            public void onSuccess(RAddToCart rAddToCart, Headers headers) {
                if (rAddToCart.isStatus()) {
                    if (rAddToCart.getData() == null) {
                        getNavigator().onErrorEditQtyFromAPI(new ApiError(Constants.IErrorCode.defaultErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
                    } else {
                        getNavigator().onSuccessEditQtyFromAPI();
                    }
                } else {
                    getNavigator().onErrorEditQtyFromAPI(new ApiError(rAddToCart.getErrorCode(), rAddToCart.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onErrorEditQtyFromAPI(t);
            }
        });
    }
}
