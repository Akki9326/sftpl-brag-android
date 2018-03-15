package com.pulse.brag.ui.authentication.profile.updateprofile;

import android.view.View;

import com.google.gson.Gson;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataUser;
import com.pulse.brag.data.model.response.RLogin;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;


/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UpdateProfileViewModel extends CoreViewModel<UpdateProfileNavigator> {
    public UpdateProfileViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onUpdateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().update();
            }
        };
    }

    public void updateProfileAPI(String firstName, String lastName, String email) {
        DataUser userData = getDataManager().getUserData();
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setEmail(email);
        Call<RLogin> responseCall = getDataManager().updateProfile(userData);
        responseCall.enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin loginResponse, Headers headers) {
                if (loginResponse.isStatus()) {
                    getDataManager().setUserData(new Gson().toJson(loginResponse.getData()));
                    getNavigator().onApiSuccess();

                } else {
                    getNavigator().onApiError(new ApiError(loginResponse.getErrorCode(), loginResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public String getFirstName() {
        return getDataManager().getUserData().getFirstName();
    }

    public String getLastName() {
        return getDataManager().getUserData().getLastName();
    }

    public String getEmail() {
        return getDataManager().getUserData().getEmail();
    }
}
