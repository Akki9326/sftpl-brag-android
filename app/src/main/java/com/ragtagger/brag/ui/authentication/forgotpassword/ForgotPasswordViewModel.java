package com.ragtagger.brag.ui.authentication.forgotpassword;

import android.databinding.ObservableField;
import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.ui.core.CoreViewModel;

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

    public void setMobileNumber(String mobileNumber) {
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

    public void sendOtp(String mobileNumber) {
        Call<RGeneralData> responeCall = getDataManager().resendOtp(mobileNumber);
        responeCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    //getNavigator().pushOtpFragment();
                    getNavigator().pushOTPFragmentOnSplashActivity();
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
