package com.ragtagger.brag.ui.authentication.profile.changepassword;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RChangePassword;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

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
        com.ragtagger.brag.data.model.requests.QChangePassword changePasswordRequest = new com.ragtagger.brag.data.model.requests.QChangePassword();
        changePasswordRequest.setMobileNumber(mobile);
        changePasswordRequest.setOldPassword(oldPass);
        changePasswordRequest.setPassword(newPass);
        Call<RChangePassword> mChangePasswordResponeCall = getDataManager().changePassword(changePasswordRequest);
        mChangePasswordResponeCall.enqueue(new ApiResponse<RChangePassword>() {
            @Override
            public void onSuccess(RChangePassword changePasswordResponse, Headers headers) {
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
        Call<RChangePassword> mChangePasswordResponeCall = ApiClient.getInstance(getActivity()).getApiResp().changePassword(changePasswordRequest);
        mChangePasswordResponeCall.enqueue(new Callback<RChangePassword>() {
            @Override
            public void onResponse(Call<RChangePassword> call, Response<RChangePassword> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    RChangePassword respone = response.body();
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
            public void onFailure(Call<RChangePassword> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getActivity(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });*/
    }
}
