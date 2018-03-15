package com.pulse.brag.ui.authentication.signup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.requests.QSignUp;
import com.pulse.brag.data.model.response.RSignUp;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class SignUpViewModel extends CoreViewModel<SignUpNavigator> {

    public SignUpViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onTypeDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().typeDropdown(v);
            }
        };
    }

    public View.OnClickListener onSignUpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().signUp();
            }
        };
    }

    public boolean onEditorActionConfirmPass(@NonNull final TextView textView, final int actionId,
                                     @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionConfirmPass(textView, actionId, keyEvent);
    }

    public void signUp(QSignUp signInRequest){
        Call<RSignUp> mSignUpResponeCall = getDataManager().userSignIn(signInRequest);
        mSignUpResponeCall.enqueue(new ApiResponse<RSignUp>() {
            @Override
            public void onSuccess(RSignUp signUpResponse, Headers headers) {
                if (signUpResponse.isStatus()){
                    getNavigator().onApiSuccess();
                    getNavigator().pushOtpFragment();
                }else {
                    getNavigator().onApiError(new ApiError(signUpResponse.getErrorCode(), signUpResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

    }
}
