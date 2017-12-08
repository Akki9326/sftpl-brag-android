package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */


/*
* ERecycleView
*
*  mRecyclerView.loadMoreComplete(true)- hide footer loader,complate load more
* */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.ColorListAdapter;
import com.pulse.brag.adapters.ProductListAdapter;
import com.pulse.brag.adapters.SizeListAdapter;
import com.pulse.brag.erecyclerview.ERecyclerView;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.pulse.brag.erecyclerview.loadmore.OnLoadMoreListener;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnAddButtonClickListener;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.pojo.DummeyRespone;
import com.pulse.brag.pojo.respones.CollectionListRespone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class ProductionListFragment extends BaseFragment implements BaseInterface,
        OnItemClickListener, OnAddButtonClickListener, OnProductSizeSelectListener,
        OnProductColorSelectListener {

    View mView;
    ERecyclerView mRecyclerView;
    RecyclerView mRecyclerViewColor, mRecyclerViewSize;
    TextView mTxtQty;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mLinearSearch;

    boolean isExecuteAsync = false;
    private static final int LOAD_LIST = 1;
    private static final int REFINE = 2;
    private static final int SORT = 3;
    private static final int SEARCH = 4;
    private static final int LOAD_MORE = 5;
    private static final int SEARCH_LOAD_MORE = 6;
    private static final int PAGE_SIZE = 5;
    private int ITEM_SPACING;
    private int ACTION = 0;
    int productCount = 60;
    private int PAGE_NUM = 1;
    private static final int QTY_REQUEST = 1;

    int mSelectedQty;
    int productSize;

    List<CollectionListRespone> collectionListRespones;
    ProductListAdapter mProductListAdapter;
    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    List<DummeyDataRespone> mDummeyDataRespones;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_product_list, container, false);
            initializeData();
            setListeners();
            ACTION = LOAD_LIST;
            checkNetworkConnection(true);
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
        ((BaseActivity) getActivity()).showToolbar(true, false, true, "Product List");
    }

    @Override
    public void initializeData() {

        mRecyclerView = (ERecyclerView) mView.findViewById(R.id.recycleView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink));
        collectionListRespones = new ArrayList<>();
        mDummeyDataRespones = new ArrayList<>();

        mLinearSearch = (LinearLayout) mView.findViewById(R.id.linear_search);

        Utility.hideSoftkeyboard(getActivity());

    }

    @Override
    public void setListeners() {


        mRecyclerView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (mDummeyDataRespones.size() != productSize) {
                            ACTION = LOAD_MORE;
                            checkNetworkConnection(false);
                        } else {
                            mRecyclerView.loadMoreComplete(true);
                        }
                    }
                }, 500);
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ACTION = LOAD_LIST;
                        PAGE_NUM = 1;
                        mRecyclerView.setIsLoadingMore(true);
                        checkNetworkConnection(false);
                    }
                }, 1000);

            }
        });
    }


    private void checkNetworkConnection(boolean isShowLoader) {

        if (Utility.isConnection(getActivity())) {

            if (ACTION == LOAD_MORE) {
                PAGE_NUM++;
            }
            GetProductListAPICall(isShowLoader);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mRecyclerView.loadMoreComplete(false);
            Utility.showAlertMessage(getActivity(), 0);
        }

//        isExecuteAsync = true;
//
//
//        isExecuteAsync = false;
//
//        mProductListAdapter.notifyDataSetChanged();
//        //stop the pull to refresh loader
//        mRecyclerView.loadMoreComplete(false);


    }

    private void GetProductListAPICall(boolean isShoeLoader) {

        if (isShoeLoader)
            showProgressDialog();

        ApiClient.changeApiBaseUrl("https://reqres.in/api/");
        Call<DummeyRespone> mDataResponeCall = ApiClient.getInstance(getActivity()).getApiResp().getProductionList(PAGE_NUM);
        mDataResponeCall.enqueue(new Callback<DummeyRespone>() {
            @Override
            public void onResponse(Call<DummeyRespone> call, Response<DummeyRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    DummeyRespone respone = response.body();
                    switch (ACTION) {
                        case LOAD_LIST:

                            productSize = 0;
                            productSize = respone.getTotal();
                            mDummeyDataRespones.clear();
                            mDummeyDataRespones.addAll(respone.getData());
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
                Utility.showAlertMessage(getContext(), t);
            }
        });
    }

    @Override
    public void showData() {


        mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mDummeyDataRespones);
        mRecyclerView.setPageSize(3);
        mRecyclerView.setAdapter(mProductListAdapter);

//        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onItemClick(int position) {
        ((BaseActivity) getActivity()).pushFragments(new ProductDetailFragment(), true, true);

        /*final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLinearSearch.getLayoutParams();
        if (params.topMargin == 10) {
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {


                    params.leftMargin = (int) (0 * interpolatedTime);
                    params.rightMargin = (int) (0 * interpolatedTime);
                    params.topMargin = (int) (0 * interpolatedTime);
                    mLinearSearch.setLayoutParams(params);
                }
            };
            a.setDuration(2000); // in ms
            mLinearSearch.startAnimation(a);
        } else {
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {


                    params.leftMargin = (int) (10 * interpolatedTime);
                    params.rightMargin = (int) (10 * interpolatedTime);
                    params.topMargin = (int) (10 * interpolatedTime);
                    mLinearSearch.setLayoutParams(params);
                }
            };
            a.setDuration(500); // in ms
            mLinearSearch.startAnimation(a);

        }
*/

    }

    @Override
    public void OnAddListener(final int position) {


        openDialogFragment(position);


    }

    private void openDialogFragment(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, mDummeyDataRespones.get(position));
        AddProductBottonDialogFragment mAddProductDialogFragment = new AddProductBottonDialogFragment();
        mAddProductDialogFragment.setArguments(bundle);
        mAddProductDialogFragment.show(getChildFragmentManager(), "");
    }

    @Override
    public void onSeleteColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
    }

    @Override
    public void OnSelectedSize(int pos) {
        mSizeListAdapter.setSelectedItem(pos);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QTY_REQUEST && resultCode == Activity.RESULT_OK) {
            mSelectedQty = data.getIntExtra(Constants.BUNDLE_QTY, 1);
            mTxtQty.setText("" + data.getIntExtra(Constants.BUNDLE_QTY, 1));
        }
    }


}
