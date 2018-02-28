package com.pulse.brag.ui.home.product.details;

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ProductDetailViewModel extends CoreViewModel<ProductDetailNavigator> {

    private final ObservableField<String> productName = new ObservableField<>();
    private final ObservableField<String> qty = new ObservableField<>();
    private final ObservableField<String> productProPrice = new ObservableField<>();
    private final ObservableField<String> productProDetail = new ObservableField<>();
    private final ObservableField<String> productProShortDetail = new ObservableField<>();
    private final ObservableField<Boolean> notify=new ObservableField<>();

    public ProductDetailViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getProductName() {
        return productName;
    }

    public void updateProductName(String productName) {
        this.productName.set(productName);
    }

    public ObservableField<String> getQty() {
        return qty;
    }

    public void updateQty(String quantity) {
        qty.set(quantity);
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

    public void updateNotifyMe(boolean notify){
        this.notify.set(notify);
    }

    public View.OnClickListener onSizeGuideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().sizeGuide();
            }
        };
    }

    public View.OnClickListener onPlusClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().plus();
            }
        };
    }

    public View.OnClickListener onMinusClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().minus();
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

    public View.OnClickListener onNotifyMeClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().notifyMe();
            }
        };
    }

    public void notifyMe(String productId, String color, String size) {
        Call<GeneralResponse> mCallNotifyMe = getDataManager().notifyMe(productId, color, size);
        mCallNotifyMe.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
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
}
