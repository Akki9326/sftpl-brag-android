package com.pulse.brag.data.remote;


import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.AppLogger;
import com.pulse.brag.utils.Common;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alpesh.rathod on 2/6/2018.
 */

public abstract class ApiResponse<T> implements Callback<T> {

    public abstract void onSuccess(T t, Headers headers);

    public abstract void onError(ApiError t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body(),response.headers());
        } else {
            onError(new ApiError(Constants.IErrorCode.defaultErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable th) {
        String val = "";
        if (!(call == null || call.request() == null || call.request().body() == null))
            val = call.request().body().toString();

        if (Common.isNotNullOrEmpty(val))
            AppLogger.e("Response Error : " + val);

        if (th instanceof UnknownHostException) {
            onError(new ApiError(Constants.IErrorCode.notInternetConnErrorCode, Constants.IErrorMessage.NO_INTERNET_CONNECTION));
        } else if (th instanceof SocketTimeoutException) {
            onError(new ApiError(Constants.IErrorCode.socketTimeOutError, Constants.IErrorMessage.TIME_OUT_CONNECTION));
        } else if (!(th instanceof IOException)) {
            onError(new ApiError(Constants.IErrorCode.defaultErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
        } else if (Common.isNotNullOrEmpty(th.getMessage()) && th.getMessage().contains("Cancel")) {
            onError(new ApiError(Constants.IErrorCode.ioExceptionCancelApiErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
        } else if (Common.isNotNullOrEmpty(th.getMessage()) && th.getMessage().equalsIgnoreCase("socket closed")) {
            onError(new ApiError(Constants.IErrorCode.ioExceptionCancelApiErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
        } else {
            onError(new ApiError(Constants.IErrorCode.ioExceptionOtherErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
        }
    }
}
