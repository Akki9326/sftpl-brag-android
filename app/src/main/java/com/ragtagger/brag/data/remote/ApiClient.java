package com.ragtagger.brag.data.remote;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;

import com.ragtagger.brag.data.local.IPreferenceManager;
import com.ragtagger.brag.utils.Constants;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class ApiClient {

    //public static final String BASE_URL = "https://reqres.in/api/";
    //public static final String BASE_URL = "http://192.168.131.123:8085/brag/api/";


    /**
     * Below part commented by alpesh on 14/02/18 because static constant moved to Constant.ApiHelper
     **/
    /*public static final String BASE_URL = "http://103.204.192.148/brag/api/";
    public static final String API_VERSION = "v1/";*/

    public static String FULL_URL = Constants.ApiHelper.BASE_URL + Constants.ApiHelper.API_VERSION;

    /*private String MAP_KEY_ACCESS_TOKEN = "access-token";
    private String MAP_KEY_DEVICE_TOKEN = "device-token";
    private String MAP_KEY_DEVICE_TYPE = "device-type";
    private String MAP_APP_VERSION = "api-version";
    private String MAP_KEY_OS = "os";
    private String MAP_KEY_OSV = "os-version";
    private String OS = "Android";*/

    /*End comment by alpesh */

    private static Retrofit retrofit = null;
    public static ApiClient apiClient;
    //static Context mContext;
    ApiInterface apiInterface;

    private IPreferenceManager mPreferencesHelper = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ApiHelper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiClient getInstance(Context context) {
        if (apiClient == null)
            apiClient = new ApiClient();
        //mContext = context;
        return apiClient;
    }

    public ApiClient() {
    }

    @Inject
    public ApiClient(Context mContext, IPreferenceManager mPreferencesHelper) {
        this.mPreferencesHelper = mPreferencesHelper;
    }

}
