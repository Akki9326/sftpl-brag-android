package com.pulse.brag.ui.contactus;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.requests.QContactUs;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class ContactUsViewModel extends CoreViewModel<ContactUsNavigator> {
    public ContactUsViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onSendClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().send();
            }
        };
    }

    public void sendMessage(String name, String email, String message) {
        Call<GeneralResponse> callContactUs = getDataManager().contactUs(new QContactUs(name, email, message));
        callContactUs.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().back();
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
