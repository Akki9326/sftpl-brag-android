package com.ragtagger.brag.ui.authentication.profile.changepassword;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.requests.QChangePassword;
import com.ragtagger.brag.data.model.response.RChangePassword;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

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
                getNavigator().performClickDone();
            }
        };
    }

    public boolean onEditorActionConfirmPass(@NonNull final TextView textView, final int actionId,
                                             @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionConfirmPass(textView, actionId, keyEvent);
    }

    void validateChangePasswordForm(Activity activity, EditText oldPassword, EditText newPassword, EditText confirmPassword) {
        if (Validation.isEmptyPassword(oldPassword)) {
            getNavigator().invalidChangePasswordForm(activity.getString(R.string.error_enter_old_pass));
        } else if (Validation.isEmptyPassword(newPassword)) {
            getNavigator().invalidChangePasswordForm(activity.getString(R.string.error_new_pass));
        } else if (Validation.isEmptyPassword(confirmPassword)) {
            getNavigator().invalidChangePasswordForm(activity.getString(R.string.error_confirm_pass));
        } else if (Validation.isTwoStringSameFormPassword(oldPassword, newPassword)) {
            getNavigator().invalidChangePasswordForm(activity.getString(R.string.error_password_not_match_with_old));
        } else if (!Validation.isTwoStringSameFormPassword(newPassword, confirmPassword)) {
            getNavigator().invalidChangePasswordForm(activity.getString(R.string.error_password_not_match));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validChangePasswordForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }

    public void changePassword(String mobile, String oldPass, String newPass) {
        QChangePassword changePasswordRequest = new QChangePassword();
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
    }
}
