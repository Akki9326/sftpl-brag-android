package com.ragtagger.brag.ui.home.product.list;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.requests.QProductList;
import com.ragtagger.brag.data.model.response.RProductList;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.callback.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ProductListViewModel extends CoreViewModel<ProductListNavigator> {

    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();


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
                getNavigator().openSortingDialog();
            }
        };
    }

    public View.OnClickListener onFilterClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().openFilterDialog();
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

    public boolean onEditorActionSearch(@NonNull final TextView textView, final int actionId,
                                        @Nullable final KeyEvent keyEvent) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            getNavigator().search(textView.getText().toString().toLowerCase());
            return true;
        }
        return false;
    }

    public void afterTextChanged(Editable s) {
        if (s != null && s.length() <= 0)
            getNavigator().search("");
    }

    public void getProductList(int page, String category, String subCategory, String seasonCode, final String q, int sorting, QProductList.Filter filter) {
        QProductList query = new QProductList();
        query.setCategory(category);
        query.setSubCategory(subCategory);
        query.setSeasonCode(seasonCode);
        query.setQ(q);
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
                        getNavigator().setData(rProductList.getData());
                        getNavigator().setProductList(rProductList.getData().getCount(), rProductList.getData().getObjects());
                        getNavigator().setFilter(rProductList.getData().getFilter());
                    } else {
                        if (q == null)
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
