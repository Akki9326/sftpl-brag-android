package com.ragtagger.brag.ui.home.product.quickadd;

import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.requests.QAddToCart;
import com.ragtagger.brag.data.model.response.RAddToCart;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Constants;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/20/2018.
 */

public class AddProductDialogViewModel extends CoreViewModel<AddProductDialogNavigator> {

    private final ObservableField<String> maxQty = new ObservableField<>();
    private final ObservableField<String> qty = new ObservableField<>();
    private final ObservableField<String> proPrice = new ObservableField<>();
    private final ObservableField<String> productName = new ObservableField<>();
    /*private final ObservableField<Boolean> notify = new ObservableField<>();*/
    private final ObservableField<Boolean> hasSizeGuide = new ObservableField<>();

    public AddProductDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getProPrice() {
        return proPrice;
    }

    public void updateProPrice(String price) {
        this.proPrice.set(price);
    }

    public ObservableField<String> getMaxQty() {
        return maxQty;
    }

    public void updateMaxQty(String maxQty) {
        this.maxQty.set(maxQty);
    }

    public ObservableField<String> getQty() {
        return qty;
    }

    public void updateQty(String qty) {
        this.qty.set(qty);
    }

    public void updateProductName(String name) {
        this.productName.set(name);
    }

    public ObservableField<String> getProductName() {
        return productName;
    }

    /*public ObservableField<Boolean> getNotify() {
        return notify;
    }

    public void updateNotifyMe(boolean notify) {
        this.notify.set(notify);
    }*/

    public ObservableField<Boolean> getHasSizeGuide() {
        return hasSizeGuide;
    }

    public void updateSizeGuide(boolean hasSizeGuide) {
        this.hasSizeGuide.set(hasSizeGuide);
    }

    public View.OnClickListener onDismissClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().dismissFragment();
            }
        };
    }

    public View.OnClickListener onEditQtyClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().editQty();
            }
        };
    }

    public View.OnClickListener onAddToCartClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().addToCart();
            }
        };
    }

    public View.OnClickListener onProductNameClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
            }
        };
    }

    public void afterTextChanged(Editable s) {
        getNavigator().afterTextChanged(s);
    }

    /*public View.OnClickListener onNotifyMeClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().notifyMe();
            }
        };
    }*/

    public void addToCart(String itemId, int qty) {
        Call<RAddToCart> callAddToCart = getDataManager().addToCart(new QAddToCart(itemId, qty));
        callAddToCart.enqueue(new ApiResponse<RAddToCart>() {
            @Override
            public void onSuccess(RAddToCart rAddToCart, Headers headers) {
                if (rAddToCart.isStatus()) {
                    if (rAddToCart.getData() == null) {
                        getNavigator().onApiError(new ApiError(Constants.IErrorCode.defaultErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
                    } else {
                        BragApp.CartNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_CART_NUM));
                        getNavigator().onApiSuccess();
                        getNavigator().onAddedToCart(rAddToCart.getData());
                    }

                } else {
                    getNavigator().onApiError(new ApiError(rAddToCart.getErrorCode(), rAddToCart.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    /*public void notifyMe(String itemNo) {
        Call<RGeneralData> mCallNotifyMe = getDataManager().notifyMe(itemNo);
        mCallNotifyMe.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().onNotifyMeSuccess("Once item available will notify you.");
                } else {
                    getNavigator().onApiError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }*/
}
