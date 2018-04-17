package com.ragtagger.brag.ui.collection;

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RCollectionList;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Constants;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public class CollectionViewModel extends CoreViewModel<CollectionNavigator> {
    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();

    private final ObservableField<Boolean> isBannerAvail = new ObservableField<>();
    private final ObservableField<Boolean> isListAvail = new ObservableField<>();

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

    public ObservableField<Boolean> getIsListAvail() {
        return isListAvail;
    }

    public void setIsListAvail(boolean isListAvail) {
        this.isListAvail.set(isListAvail);
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

    public void callGetCollectionListApi() {
        Call<RCollectionList> getCategoryList = getDataManager().getCollectionProduct();
        getCategoryList.enqueue(new ApiResponse<RCollectionList>() {
            @Override
            public void onSuccess(RCollectionList collectionListResponse, Headers headers) {
                if (collectionListResponse.isStatus()) {
                    BragApp.CartNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_CART_NUM));
                    BragApp.NotificationNumber = Integer.parseInt(headers.get(Constants.ApiHelper.MAP_KEY_NOTIFICATION_NUM));
                    getNavigator().onApiSuccess();
                    if (collectionListResponse.getData() == null) {
                        getNavigator().onNoData();
                    } else if (collectionListResponse.getData().getBanners().size() == 0 && collectionListResponse.getData().getCategories().size() == 0) {
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
