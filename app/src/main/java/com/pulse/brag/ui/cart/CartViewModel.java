package com.pulse.brag.ui.cart;


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

import com.pulse.brag.BragApp;
import com.pulse.brag.R;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.requests.QAddToCart;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.Constants;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public class CartViewModel extends CoreViewModel<CartNavigator> {

    RCartList response;
    ObservableField<String> total = new ObservableField<>();
    ObservableField<Integer> listNum = new ObservableField<>();
    ObservableField<Boolean> visibility = new ObservableField<>();
    ObservableField<String> listSize = new ObservableField<>();
    ObservableField<Boolean> noInternet = new ObservableField<>();
    String itemsLable;

    public CartViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void getCartData() {
        Call<RCartList> cartListResponseCall = getDataManager().getCartList("item/getCart");
        cartListResponseCall.enqueue(new ApiResponse<RCartList>() {
            @Override
            public void onSuccess(RCartList cartListResponse, Headers headers) {
                if (cartListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().getCartList(cartListResponse.getData());
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

    public void removeFromCart(String itemId) {
        Call<GeneralResponse> cartListResponseCall = getDataManager().removeFromCart("item/removeFromCart/" + itemId);
        cartListResponseCall.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse cartListResponse, Headers headers) {
                if (cartListResponse.isStatus()) {
//                    BragApp.CartNumber--;
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

    public ObservableField<String> getTotalPrice() {
        return total;
    }

    public ObservableField<Boolean> getListVisibility() {
        return visibility;
    }

    public void setListVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }


    public void setTotal(String total) {
        this.total.set(total);
    }

    public void setListNum(int count) {
        this.listNum.set(count);
    }


    public View.OnClickListener onContinues() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onContinuesClick();
            }
        };
    }

    public View.OnClickListener onPriceClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onPriceClick();
            }
        };
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


    public void editQty(String id, String itemId, int qty) {
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

    public ObservableField<Boolean> getNoInternet() {
        return noInternet;
    }

    public void setNoInternet(boolean noInternet) {
        this.noInternet.set(noInternet);
    }
}
