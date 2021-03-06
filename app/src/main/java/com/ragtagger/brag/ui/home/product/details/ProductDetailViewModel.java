package com.ragtagger.brag.ui.home.product.details;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.requests.QAddToCart;
import com.ragtagger.brag.data.model.response.RAddToCart;
import com.ragtagger.brag.data.model.response.RProduct;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Constants;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ProductDetailViewModel extends CoreViewModel<ProductDetailNavigator> {

    private final ObservableField<String> qty = new ObservableField<>();
    private final ObservableField<String> maxQty = new ObservableField<>();
    private final ObservableField<String> productProPrice = new ObservableField<>();
    private final ObservableField<String> productProDetail = new ObservableField<>();
    private final ObservableField<String> productProShortDetail = new ObservableField<>();
    private final ObservableField<Boolean> notify = new ObservableField<>();
    private final ObservableField<Boolean> hasSizeGuide = new ObservableField<>();
    ObservableField<Boolean> isLoading = new ObservableField<>();

    public ProductDetailViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public void updateIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public ObservableField<String> getQty() {
        return qty;
    }

    public void updateQty(String quantity) {
        qty.set(quantity);
    }

    public ObservableField<String> getMaxQty() {
        return maxQty;
    }

    public void updateMaxQty(String maxQty) {
        this.maxQty.set(maxQty);
    }

    public ObservableField<String> getProductProPrice() {
        return productProPrice;
    }

    public void updateProductProPrice(String proPrice) {
        this.productProPrice.set(proPrice);
    }

    public ObservableField<String> getProductProShortDetail() {
        return productProShortDetail;
    }

    public void updateProductProShortDetail(String proShortDetail) {
        this.productProShortDetail.set(proShortDetail);
    }

    public ObservableField<String> getProductProDetail() {
        return productProDetail;
    }

    public void updateProductProDetail(String proDetail) {
        this.productProDetail.set(proDetail);
    }

    public ObservableField<Boolean> getNotify() {
        return notify;
    }

    public void updateNotifyMe(boolean notify) {
        this.notify.set(notify);
    }

    public ObservableField<Boolean> getHasSizeGuide() {
        return hasSizeGuide;
    }

    public void updateSizeGuide(boolean hasSizeGuide) {
        this.hasSizeGuide.set(hasSizeGuide);
    }

    public View.OnClickListener onSizeGuideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSizeGuide();
            }
        };
    }

    public void afterTextChanged(Editable s) {
        getNavigator().afterTextChanged(s);
    }

    public boolean onEditorActionHide(@NonNull final TextView textView, final int actionId,
                                      @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionHide(textView, actionId, keyEvent);
    }

    public View.OnClickListener onPlusClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNavigator().performClickPlus();
            }
        };
    }

    public View.OnClickListener onMinusClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNavigator().performClickMinus();
            }
        };
    }

    public View.OnClickListener onEditTextQtyClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNavigator().onEditTextQty();
            }
        };
    }

    public View.OnClickListener onAddToCartClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performAddToCartClick();
            }
        };
    }

    public View.OnClickListener onNotifyMeClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickNotifyMe();
            }
        };
    }


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

    public void notifyMe(String itemNo) {
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
    }

    public void getProductDetail(String id) {
        Call<RProduct> productCall = getDataManager().getProductDetail(id);
        productCall.enqueue(new ApiResponse<RProduct>() {
            @Override
            public void onSuccess(RProduct rProduct, Headers headers) {
                if (rProduct.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().setProductDetails(rProduct.getData());
                    BragApp.CartNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_CART_NUM));
                    getNavigator().updatePushCart();
                } else {
                    getNavigator().onApiError(new ApiError(rProduct.getErrorCode(), rProduct.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }


}
