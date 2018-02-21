package com.pulse.brag.ui.createnewpassord;

import android.view.View;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.response.ChangePasswordResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;

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

        /*Call<ChangePasswordResponse> mChangePasswordResponeCall = ApiClient.getInstance(getActivity()).getApiResp().resetPassword(resetPassword);
        mChangePasswordResponeCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    ChangePasswordResponse respone = response.body();
                    if (respone.isStatus()) {
                        showAlertMessage();
                    } else {
                        //Utility.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                    }
                } else {
                    //Utility.showAlertMessage(getActivity(), 1, null);
                    AlertUtils.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getActivity(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });*/
    }
}
