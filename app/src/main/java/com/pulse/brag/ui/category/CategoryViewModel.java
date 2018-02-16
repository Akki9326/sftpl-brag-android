package com.pulse.brag.ui.category;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.datas.CategoryListResponseData;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.views.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryViewModel extends CoreViewModel<CategoryNavigator> {

    public CategoryViewModel(IDataManager dataManager) {
        super(dataManager);
    }


    public void getCategoryData() {
        Call<CategoryListResponse> mCategoryRespone = getDataManager().getCategoryProduct("home/get/1");
        mCategoryRespone.enqueue(new ApiResponse<CategoryListResponse>() {
            @Override
            public void onSuccess(CategoryListResponse categoryListResponse, Headers headers) {
                if (categoryListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                } else {
                    getNavigator().onApiError(new ApiError(categoryListResponse.getErrorCode(), categoryListResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }


}
