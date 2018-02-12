package com.pulse.brag.data.remote;

import android.content.Context;

import com.pulse.brag.di.PreferenceInfo;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.LoginResponse;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public class AppApiManager implements IApiManager {

    private final ApiInterface mApiInterface;
    @Inject
    public AppApiManager(Context context, ApiInterface apiInterface) {
        this.mApiInterface = apiInterface;
    }


    @Override
    public Call<LoginResponse> userLogin(LoginRequest loginRequest) {
        return mApiInterface.userLogin(loginRequest);
    }
}
