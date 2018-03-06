package com.pulse.brag.ui.home.category;


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
import com.pulse.brag.data.model.datas.CategoryListResponseData;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import java.util.Collections;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryViewModel extends CoreViewModel<CategoryNavigator> {

    private final ObservableField<Boolean> isBannerAvail = new ObservableField<>();

    public CategoryViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<Boolean> getIsBannerAvail() {
        return isBannerAvail;
    }

    public void setIsBannerAvail(boolean avail) {
        isBannerAvail.set(avail);
    }

    public void getCategoryData() {
        Call<CategoryListResponse> mCategoryRespone = getDataManager().getCategoryProduct("home/get/1");
        mCategoryRespone.enqueue(new ApiResponse<CategoryListResponse>() {
            @Override
            public void onSuccess(CategoryListResponse categoryListResponse, Headers headers) {
                if (categoryListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().setBanner(categoryListResponse.getData().getBanners());
                    getNavigator().setCategoryList(categoryListResponse.getData().getCategories());
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

}
