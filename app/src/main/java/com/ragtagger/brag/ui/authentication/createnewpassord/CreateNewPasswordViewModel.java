package com.ragtagger.brag.ui.authentication.createnewpassord;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RChangePassword;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

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
                getNavigator().performClickDone();
            }
        };
    }

    void validateNewPasswordForm(Activity activity, EditText password, EditText confirmPassword) {
        if (Validation.isEmptyPassword(password)) {
            getNavigator().invalidNewPasswordForm(activity.getString(R.string.error_new_pass));
        } else if (Validation.isEmptyPassword(confirmPassword)) {
            getNavigator().invalidNewPasswordForm(activity.getString(R.string.error_confirm_pass));
        } else if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
            getNavigator().invalidNewPasswordForm(activity.getString(R.string.error_password_not_match));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validNewPasswordForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }


    public void callChangePasswordApi(String mobile, String otp, String newPassword) {
        com.ragtagger.brag.data.model.requests.QChangePassword resetPassword = new com.ragtagger.brag.data.model.requests.QChangePassword();
        resetPassword.setMobileNumber(mobile);
        resetPassword.setOtp(Integer.parseInt(otp));
        resetPassword.setPassword(newPassword);

        Call<RChangePassword> mChangePasswordResponeCall = getDataManager().resetPassword(resetPassword);
        mChangePasswordResponeCall.enqueue(new ApiResponse<RChangePassword>() {
            @Override
            public void onSuccess(RChangePassword changePasswordResponse, Headers headers) {
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
