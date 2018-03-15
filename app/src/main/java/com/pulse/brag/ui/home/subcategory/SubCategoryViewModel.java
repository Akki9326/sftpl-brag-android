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
import com.pulse.brag.data.model.response.RCategoryList;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public class SubCategoryViewModel extends CoreViewModel<SubCategoryNavigator> {
    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();

    ObservableField<String> productImg = new ObservableField<>();

    public SubCategoryViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<Boolean> getNoInternet() {
        return noInternet;
    }

    public void setNoInternet(boolean noInternet) {
        this.noInternet.set(noInternet);
    }

    public ObservableField<Boolean> getNoResult() {
        return noResult;
    }

    public void setNoResult(boolean noResult) {
        this.noResult.set(noResult);
    }

    public void getSubCategoryData() {
        Call<RCategoryList> mCategoryRespone = getDataManager().getCategoryProduct();
        mCategoryRespone.enqueue(new ApiResponse<RCategoryList>() {
            @Override
            public void onSuccess(RCategoryList categoryListResponse, Headers headers) {
                if (categoryListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    // TODO: 27-02-2018 :onSwiptoRefresh
                    //getNavigator().OnSuccessPullToRefresh(categoryListResponse.getData());

                    if (categoryListResponse.getData() != null && categoryListResponse.getData().getCategories() != null && categoryListResponse.getData().getCategories().size() > 0) {
                        getNavigator().setCategoryList(categoryListResponse.getData().getCategories());
                    } else {
                        getNavigator().onNoData();
                    }


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
