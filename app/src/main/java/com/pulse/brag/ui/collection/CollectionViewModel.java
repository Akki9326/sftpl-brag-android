package com.pulse.brag.ui.collection;

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.pulse.brag.R;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.AlertUtils;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public class CollectionViewModel extends CoreViewModel<CollectionNavigator> {
    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();

    private final ObservableField<Boolean> isBannerAvail = new ObservableField<>();

    public CollectionViewModel(IDataManager dataManager) {
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

    public void getCollectionList() {
        Call<CollectionListResponse> getCategoryList = getDataManager().getCollectionProduct();
        getCategoryList.enqueue(new ApiResponse<CollectionListResponse>() {
            @Override
            public void onSuccess(CollectionListResponse collectionListResponse, Headers headers) {
                if (collectionListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    if (collectionListResponse.getData() == null) {
                        getNavigator().onNoData();
                    } else {
                        getNavigator().setCategoryList(collectionListResponse.getData().getCategories());
                        getNavigator().setBanner(collectionListResponse.getData().getBanners());
                    }
                } else {
                    getNavigator().onApiError(new ApiError(collectionListResponse.getErrorCode(), collectionListResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }
}
