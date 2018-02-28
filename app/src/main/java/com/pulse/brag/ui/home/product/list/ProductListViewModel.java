package com.pulse.brag.ui.home.product.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.pulse.brag.R;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.DummeyRespone;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ProductListViewModel extends CoreViewModel<ProductListNavigator> {


    public ProductListViewModel(IDataManager dataManager) {
        super(dataManager);
    }


    public View.OnClickListener onSortClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().sort();
            }
        };
    }

    public View.OnClickListener onFilterClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().filter();
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

    public void getProductList(Context context, int page) {

        //Call<DummeyRespone> mDataResponeCall = getDataManager().getProductionList(page);

        // TODO: 2/19/2018  replace below 2 line with above one line and check url in ApiInterface class
        ApiClient.changeApiBaseUrl("https://reqres.in/api/");
        Call<DummeyRespone> mDataResponeCall = ApiClient.getInstance(context).getApiResp().getProductionList(page);
        mDataResponeCall.enqueue(new ApiResponse<DummeyRespone>() {
            @Override
            public void onSuccess(DummeyRespone dummeyRespone, Headers headers) {
                getNavigator().onApiSuccess();
                getNavigator().showList(dummeyRespone.getTotal(), dummeyRespone.getData());
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

        /*ApiClient.changeApiBaseUrl("https://reqres.in/api/");
        Call<DummeyRespone> mDataResponeCall = ApiClient.getInstance(getActivity()).getApiResp().getProductionList(PAGE_NUM);

        mDataResponeCall.enqueue(new Callback<DummeyRespone>() {
            @Override
            public void onResponse(Call<DummeyRespone> call, Response<DummeyRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    DummeyRespone respone = response.body();
                    switch (ACTION) {
                        case LOAD_LIST:

                            productListSize = 0;
                            productListSize = respone.getTotal();
                            mDummeyDataRespones.clear();

                            mDummeyDataRespones.add(new DummeyDataRespone(0,
                                    "Classic Pullover T-shirt Bralette - Black Solid body with Black trims",
                                    "",
                                    "http://cdn.shopify.com/s/files/1/1629/9535/products/Chandini_SLIDE_Pattern_B_BRAG-Optical59961_large.jpg?v=1516609967"));

                            mDummeyDataRespones.add(new DummeyDataRespone(0,
                                    "Classic Pullover T-shirt Bralette - White with Black Print & Black trims",
                                    "",
                                    "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG-Optical60283_large.jpg?v=1516609966"));

                            mDummeyDataRespones.add(new DummeyDataRespone(0,
                                    "Classic Pullover T-shirt Bralette - White with Black Print & Black trims",
                                    "",
                                    "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG-Optical59472_large.jpg?v=1516609967"));


//                            mDummeyDataRespones.addAll(respone.getData());
                            showData();
                            mSwipeRefreshLayout.setRefreshing(false);
                            break;
                        case LOAD_MORE:

                            mDummeyDataRespones.addAll(respone.getData());
                            mProductListAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete(false);
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<DummeyRespone> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getContext(), t);
                AlertUtils.showAlertMessage(getContext(), t);
            }
        });*/
    }
}
