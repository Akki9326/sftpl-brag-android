package com.pulse.brag.ui.profile.changepassword;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.response.ChangePasswordResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.views.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class ChangePassViewModel extends CoreViewModel<ChangePassNavigator> {
    public ChangePassViewModel(IDataManager dataManager) {
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

    public boolean onEditorActionConfirmPass(@NonNull final TextView textView, final int actionId,
                                             @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionConfirmPass(textView, actionId, keyEvent);
    }

    public void changePassword(String mobile, String oldPass, String newPass) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setMobileNumber(mobile);
        changePasswordRequest.setOldPassword(oldPass);
        changePasswordRequest.setPassword(newPass);
        Call<ChangePasswordResponse> mChangePasswordResponeCall = getDataManager().changePassword(changePasswordRequest);
        mChangePasswordResponeCall.enqueue(new ApiResponse<ChangePasswordResponse>() {
            @Override
            public void onSuccess(ChangePasswordResponse changePasswordResponse, Headers headers) {
                if (changePasswordResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().showMsgPasswordChange();
                } else {
                    getNavigator().onApiError(new ApiError(changePasswordResponse.getErrorCode(), changePasswordResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

        /*ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<ChangePasswordResponse> mChangePasswordResponeCall = ApiClient.getInstance(getActivity()).getApiResp().changePassword(changePasswordRequest);
        mChangePasswordResponeCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    ChangePasswordResponse respone = response.body();
                    if (respone.isStatus()) {
                        //Utility.showAlertMessage(getContext(), getString(R.string.msg_password_change_successfull), true);
                        AlertUtils.showAlertMessage(getContext(), getString(R.string.msg_password_change_successfull), true);
                    } else {
                        //Utility.showAlertMessage(getActivity(), respone.getErrorCode(),respone.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                    }
                } else {
                    //Utility.showAlertMessage(getActivity(), 1,null);
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
