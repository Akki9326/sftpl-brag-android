package com.ragtagger.brag.ui.home.category;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RCategoryList;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Constants;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryViewModel extends CoreViewModel<CategoryNavigator> {

    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();

    private final ObservableField<Boolean> isBannerAvail = new ObservableField<>();

    private final ObservableField<Boolean> isListAvail = new ObservableField<>();

    public CategoryViewModel(IDataManager dataManager) {
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

    public ObservableField<Boolean> getIsBannerAvail() {
        return isBannerAvail;
    }

    public void setIsBannerAvail(boolean isBannerAvail) {
        this.isBannerAvail.set(isBannerAvail);
    }

    public ObservableField<Boolean> getIsListAvail() {
        return isListAvail;
    }

    public void setIsListAvail(boolean isListAvail) {
        this.isListAvail.set(isListAvail);
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

    public void getCategoryData() {
        Call<RCategoryList> mCategoryRespone = getDataManager().getCategoryProduct();
        mCategoryRespone.enqueue(new ApiResponse<RCategoryList>() {
            @Override
            public void onSuccess(RCategoryList categoryListResponse, Headers headers) {
                if (categoryListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    BragApp.CartNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_CART_NUM));
                    BragApp.NotificationNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_NOTIFICATION_NUM));
                    if (categoryListResponse.getData() == null) {
                        getNavigator().onNoData();
                    } else if (categoryListResponse.getData().getBanners().size() == 0 && categoryListResponse.getData().getCategories().size() == 0) {
                        getNavigator().onNoData();
                    } else {
                        getNavigator().setBanner(categoryListResponse.getData().getBanners());
                        getNavigator().setCategoryList(categoryListResponse.getData().getCategories());
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


}
