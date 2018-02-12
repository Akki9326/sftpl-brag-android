package com.pulse.brag.data.remote;

import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.LoginResponse;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface IApiManager {

    Call<LoginResponse> userLogin(LoginRequest loginRequest);
}
