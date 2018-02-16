package com.pulse.brag.ui.forgotpassword;

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.views.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

public class ForgotPasswordViewModel extends CoreViewModel<ForgotPasswordNavigator> {

    private final ObservableField<String> mobileNumber = new ObservableField<>();

    public ForgotPasswordViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void setMobileNumber(String mobileNumber){
        this.mobileNumber.set(mobileNumber);
    }

    public ObservableField<String> getMobileNumber() {
        return mobileNumber;
    }



    public View.OnClickListener onSendOtpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().sendOtp();
            }
        };
    }

    public void sendOtp(String mobileNumber){
        Call<GeneralResponse> responeCall = getDataManager().resendOtp(mobileNumber);
        responeCall.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().pushOtpFragment();
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
