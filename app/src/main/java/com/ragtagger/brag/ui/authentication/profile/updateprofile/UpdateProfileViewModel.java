package com.ragtagger.brag.ui.authentication.profile.updateprofile;

import android.view.View;

import com.google.gson.Gson;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.callback.OnSingleClickListener;

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

    public void updateProfileAPI(String firstName, String lastName, String email, String gstNum) {
        DataUser userData = getDataManager().getUserData();
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setEmail(email);
        userData.setGstin(gstNum);
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

    public String gstNum() {
        return getDataManager().getUserData().getGstin();
    }
}
