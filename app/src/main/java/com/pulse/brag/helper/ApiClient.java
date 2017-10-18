package com.pulse.brag.helper;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;

import com.pulse.brag.interfaces.ApiInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class ApiClient {

    public static final String BASE_URL = "http://api.themoviedb.org/";
    public static final String API_PATH_POST = "3/";
    private static Retrofit retrofit = null;
    public static ApiClient apiClient;
    static Context mContext;
    ApiInterface apiInterface;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiClient getInstance(Context context) {
        if (apiClient == null)
            apiClient = new ApiClient();
        mContext = context;
        return apiClient;
    }

    public ApiInterface getWebApiWithToken() {
        try {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
//                            .header(KEY_VERSION_NAME,VERSION_NAME)
//                            .header(KEY_ACCESS_TOKEN,ACCESS_TOKEN)
//                            .header(KEY_DEVICE_TOKEN,DEVICE_TOKEN)
//                            .header(KEY_DEVICE_TYPE,DEVICE_TYPE)
//                            .header(KEY_OSV,OSV)
//                            .header(KEY_OS,OS)
                            .method(chain.request().method(), chain.request().body())
                            .build();
                    return chain.proceed(newRequest);
                }
            };
            httpClient.addInterceptor(interceptor);
            httpClient.addInterceptor(logging);

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL + API_PATH_POST).addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
            return apiInterface;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
