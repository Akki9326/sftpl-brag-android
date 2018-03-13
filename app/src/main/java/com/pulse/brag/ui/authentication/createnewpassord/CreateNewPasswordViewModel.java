package com.pulse.brag.ui.authentication.createnewpassord;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.requests.ChangePasswordRequest;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class CreateNewPasswordViewModel extends CoreViewModel<CreateNewPasswordNavigator> {

    public CreateNewPasswordViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onDoneClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().done();
            }
        };
    }


    public void changePassword(String mobile, String otp, String newPassword) {
        ChangePasswordRequest resetPassword = new ChangePasswordRequest();
        resetPassword.setMobileNumber(mobile);
        resetPassword.setOtp(Integer.parseInt(otp));
        resetPassword.setPassword(newPassword);

        Call<ChangePasswordResponse> mChangePasswordResponeCall = getDataManager().resetPassword(resetPassword);
        mChangePasswordResponeCall.enqueue(new ApiResponse<ChangePasswordResponse>() {
            @Override
            public void onSuccess(ChangePasswordResponse changePasswordResponse, Headers headers) {
                if (changePasswordResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().onChangePasswordSuccess();
                } else {
                    getNavigator().onApiError(new ApiError(changePasswordResponse.getErrorCode(), changePasswordResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

    }
}