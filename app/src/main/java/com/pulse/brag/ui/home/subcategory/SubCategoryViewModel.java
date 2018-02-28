package com.pulse.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.pulse.brag.R;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public class SubCategoryViewModel extends CoreViewModel<SubCategoryNavigator> {

    ObservableField<String> productImg = new ObservableField<>();

    public SubCategoryViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void getSubCategoryData() {
        Call<CategoryListResponse> mCategoryRespone = getDataManager().getCategoryProduct("home/get/1");
        mCategoryRespone.enqueue(new ApiResponse<CategoryListResponse>() {
            @Override
            public void onSuccess(CategoryListResponse categoryListResponse, Headers headers) {
                if (categoryListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    // TODO: 27-02-2018 :onSwiptoRefresh
//                    getNavigator().OnSuccessPullToRefresh(categoryListResponse.getData());
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

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNavigator().swipeRefresh();
            }
        };
    }

    public int[] getColorSchemeResources() {
        return new int[]{
                R.color.pink,
        };
    }


    public void setProductImg(String url) {
        productImg.set(url);
    }

    public ObservableField<String> getProductImg() {
        return productImg;
    }
}
