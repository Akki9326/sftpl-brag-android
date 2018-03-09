package com.pulse.brag.ui.home.product.list;

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.pulse.brag.R;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataProductList;
import com.pulse.brag.data.model.requests.QProductList;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ProductListViewModel extends CoreViewModel<ProductListNavigator> {

    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();

    ObservableField<String> productImg = new ObservableField<>();

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


    public ProductListViewModel(IDataManager dataManager) {
        super(dataManager);
    }


    public View.OnClickListener onSortClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().openSortDialog();
            }
        };
    }

    public View.OnClickListener onFilterClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().openFilter();
            }
        };
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

    public void getProductList(int page, String category, String subCategory, int sorting, DataProductList.Filter filter) {
        QProductList query = new QProductList();
        query.setCategory(category);
        query.setSubCategory(subCategory);
        query.setSeasonCode(null);
        query.setOrderByEnum(sorting);
        query.setFilter(filter);
        Call<RProductList> getProductList = getDataManager().getProductionList(page, query);
        getProductList.enqueue(new ApiResponse<RProductList>() {
            @Override
            public void onSuccess(RProductList rProductList, Headers headers) {
                if (rProductList.isStatus()) {
                    getNavigator().onApiSuccess();
                    if (rProductList.getData() != null && rProductList.getData().getObjects() != null && rProductList.getData().getObjects().size() > 0) {
                        //display data
                        getNavigator().setProductList(rProductList.getData().getCount(), rProductList.getData().getObjects());
                        getNavigator().setFilter(rProductList.getData().getFilter());
                    } else {
                        getNavigator().onNoData();
                    }
                } else {
                    getNavigator().onApiError(new ApiError(rProductList.getErrorCode(), rProductList.getMessage()));
                }

            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });


    }
}
