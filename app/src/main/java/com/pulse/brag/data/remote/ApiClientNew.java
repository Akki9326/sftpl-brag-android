package com.pulse.brag.data.remote;

import android.content.Context;

import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.utils.Constants;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alpesh.rathod on 2/13/2018.
 */

public class ApiClientNew {

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
    private String MAP_API_VERSION = "api-version";
    private String MAP_KEY_OS = "os";
    private String MAP_KEY_OSV = "os-version";
    private String OS = "Android";*/

    /*End comment by alpesh */


    private static Retrofit retrofit = null;
    public static ApiClientNew apiClient;
    //static Context mContext;
    ApiInterface apiInterface;

    private IPreferenceManager mPreferencesHelper = null;

    @Inject
    public ApiClientNew(Context mContext, IPreferenceManager mPreferencesHelper) {
        this.mPreferencesHelper = mPreferencesHelper;
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

                    if (!mPreferencesHelper.getAccessToken().isEmpty()) {
                        builder.header(Constants.ApiHelper.MAP_KEY_ACCESS_TOKEN, mPreferencesHelper.getAccessToken());
                    }

                    if (!mPreferencesHelper.getDeviceType().isEmpty()) {
                        builder.header(Constants.ApiHelper.MAP_KEY_DEVICE_TYPE, mPreferencesHelper.getDeviceType());
                    }

                    if (!mPreferencesHelper.getDeviceToken().isEmpty()) {
                        builder.header(Constants.ApiHelper.MAP_KEY_DEVICE_TOKEN, mPreferencesHelper.getDeviceToken());
                    }


                    if (!mPreferencesHelper.getOsVersion().isEmpty()) {
                        builder.header(Constants.ApiHelper.MAP_KEY_OSV, mPreferencesHelper.getOsVersion());
                    }

                    builder.header(Constants.ApiHelper.MAP_KEY_OS, Constants.ApiHelper.OS);
                    builder.header(Constants.ApiHelper.MAP_API_VERSION, Constants.ApiHelper.API_VERSION.replace("/", ""));
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
