package com.ragtagger.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.data.model.requests.QSubCategory;
import com.ragtagger.brag.data.model.response.RSubCategory;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RCategoryList;
import com.ragtagger.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public class SubCategoryViewModel extends CoreViewModel<SubCategoryNavigator> {
    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();

    ObservableField<String> productImg = new ObservableField<>();

    ObservableField<DataCategoryList.Category> mSelectedCategoryObject = new ObservableField<>();

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

    public ObservableField<String> getProductImg() {
        return productImg;
    }

    public void setProductImg(String url) {
        productImg.set(url);
    }

    public ObservableField<DataCategoryList.Category> getSelectedCategory() {
        return mSelectedCategoryObject;
    }

    public void setSelectedCategory(DataCategoryList.Category mSelectedCategory) {
        mSelectedCategoryObject.set(mSelectedCategory);
    }


    public SwipeRefreshLayout.OnRefreshListener setOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNavigator().performSwipeRefresh();
            }
        };
    }

    public int[] getColorSchemeResources() {
        return new int[]{
                R.color.pink,
        };
    }

    void callGetSubCategoryDataApi(int pageNumber, String categoryId) {
        QSubCategory mQSubCategory = new QSubCategory();
        getDataManager().getSubCategory(pageNumber, categoryId, mQSubCategory).enqueue(new ApiResponse<RSubCategory>() {
            @Override
            public void onSuccess(RSubCategory rSubCategory, Headers headers) {
                if (rSubCategory.isStatus()) {
                    getNavigator().onApiSuccess();
                    if (rSubCategory.getData() != null && rSubCategory.getData().getObjects() != null
                            && rSubCategory.getData().getObjects().size() > 0) {
                        getNavigator().setCategoryList(rSubCategory.getData().getCount(),rSubCategory.getData().getObjects());
                    } else {
                        getNavigator().onNoData();
                    }
                } else {
                    getNavigator().onApiError(new ApiError(rSubCategory.getErrorCode(), rSubCategory.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }
}

