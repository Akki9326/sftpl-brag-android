package com.pulse.brag.ui.home.product.quickadd;

import android.databinding.ObservableField;
import android.text.Editable;
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
 * Created by alpesh.rathod on 2/20/2018.
 */

public class AddProductDialogViewModel extends CoreViewModel<AddProductDialogNavigator> {

    private final ObservableField<String> maxQty= new ObservableField<>();
    private final ObservableField<String> productName=new ObservableField<>();
    private final ObservableField<Boolean> notify = new ObservableField<>();

    public AddProductDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getMaxQty() {
        return maxQty;
    }

    public void updateMaxQty(String maxQty){
        this.maxQty.set(maxQty);
    }

    public void updateProductName(String name){
        this.productName.set(name);
    }

    public ObservableField<String> getProductName() {
        return productName;
    }

    public ObservableField<Boolean> getNotify() {
        return notify;
    }

    public void updateNotifyMe(boolean notify){
        this.notify.set(notify);
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

    public void afterTextChanged(Editable s){
        getNavigator().afterTextChanged(s);
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
