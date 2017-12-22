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

//    public static final String BASE_URL = "https://reqres.in/api/";
//    public static final String BASE_URL = "http://192.168.131.123:8085/brag/api/";
    public static final String BASE_URL = "http://103.204.192.148/brag/api/";

    public static final String API_VERSION = "v1/";

        public static String FULL_URL = BASE_URL + API_VERSION;
//    public static final String FULL_URL = BASE_URL;

    private String MAP_KEY_ACCESS_TOKEN = "access-token";
    private String MAP_KEY_DEVICE_TOKEN = "device-token";
    private String MAP_KEY_DEVICE_TYPE = "device-type";
    private String MAP_API_VERSION = "api-version";
    private String MAP_KEY_OS = "os";
    private String MAP_KEY_OSV = "os-version";
    private String OS = "Android";

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

    public ApiInterface getApiResp() {
        try {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    if (!PreferencesManager.getInstance().getAccessToken().isEmpty()) {
                        builder.header(MAP_KEY_ACCESS_TOKEN, PreferencesManager.getInstance().getAccessToken());
                    }
                    if (!PreferencesManager.getInstance().getDeviceType().isEmpty()) {
                        builder.header(MAP_KEY_DEVICE_TYPE, PreferencesManager.getInstance().getDeviceType());
                    }
                    if (!PreferencesManager.getInstance().getDeviceToken().isEmpty()) {
                        builder.header(MAP_KEY_DEVICE_TOKEN, PreferencesManager.getInstance().getDeviceToken());
                    }
                    if (!PreferencesManager.getInstance().getOsVersion().isEmpty()) {
                        builder.header(MAP_KEY_OSV, PreferencesManager.getInstance().getOsVersion());
                    }
                    builder.header(MAP_KEY_OS, OS);
                    builder.header(MAP_API_VERSION, API_VERSION.replace("/", ""));
                    return chain.proceed(builder.build());
                }
            };

            httpClient.addInterceptor(interceptor);
            httpClient.addInterceptor(logging);

            OkHttpClient client = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(FULL_URL).addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
            return apiInterface;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public static void changeApiBaseUrl(String newApiBaseUrl) {
        FULL_URL = newApiBaseUrl;

        retrofit = new Retrofit.Builder()
                .baseUrl(FULL_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
