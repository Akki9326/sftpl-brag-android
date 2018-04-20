package com.ragtagger.brag.ui.cart.onbehalf;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.EditText;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RCheckCustomer;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;

public class OnBehalfDialogViewModel extends CoreViewModel<OnBehalfDialogNavigator> {
    ObservableField<String> customer = new ObservableField<>();

    public OnBehalfDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public View.OnClickListener onDoneClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickDone(v);
            }
        };
    }

    void validateCustomerIdForm(Activity activity, EditText customerId) {
        if (Validation.isEmpty(customerId)) {
            getNavigator().invalidCustomerIdForm(activity.getString(R.string.error_empty_customer_code));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validCustomerIdForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }

    void callCheckCustomerApi(String customerCode) {
        getDataManager().checkCustomer(customerCode).enqueue(new ApiResponse<RCheckCustomer>() {
            @Override
            public void onSuccess(RCheckCustomer rCheckCustomer, Headers headers) {
                if (rCheckCustomer.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().setCustomerData(rCheckCustomer.getData());
                } else {
                    getNavigator().onApiError(new ApiError(rCheckCustomer.getErrorCode(), rCheckCustomer.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }
}
