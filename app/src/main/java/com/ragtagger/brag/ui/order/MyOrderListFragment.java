package com.ragtagger.brag.ui.order;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.response.RMyOrderList;
import com.ragtagger.brag.ui.order.adapter.MyOrderListAdapter;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentMyOrderBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.order.orderdetail.OrderDetailFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.ragtagger.brag.views.erecyclerview.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class MyOrderListFragment extends CoreFragment<FragmentMyOrderBinding, MyOrderViewModel> implements MyOrderNavigator, MyOrderListAdapter.OnItemClick {

    private int ACTION = 0;
    private static final int LOAD_LIST = 1;
    private static final int LOAD_MORE = 5;
    private int PAGE_NUM = 1;
    int totalOrderList;

    @Inject
    MyOrderViewModel mMyOrderViewModel;
    FragmentMyOrderBinding mFragmentMyOrderBinding;

    List<DataMyOrder> mList;
    MyOrderListAdapter listAdapter;

    private OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (mFragmentMyOrderBinding.swipeRefreshLayout.isRefreshing()) {
                        return;
                    }

                    if (mList.size() != totalOrderList) {
                        ACTION = LOAD_MORE;
                        checkInternet(false);
                    } else {
                        mFragmentMyOrderBinding.recycleview.loadMoreComplete(true);
                    }
                }
            }, 500);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyOrderViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentMyOrderBinding = getViewDataBinding();


        initializeData();

        mFragmentMyOrderBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternet(true);
            }
        });


        ACTION = LOAD_LIST;
        checkInternet(true);

    }

    @Override
    public void setUpToolbar() {
        ((MainActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_my_order));
    }

    @Override
    public MyOrderViewModel getViewModel() {
        return mMyOrderViewModel;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    private void checkInternet(boolean isShowLoader) {

        if (Utility.isConnection(getActivity())) {
            mMyOrderViewModel.setNoInternet(false);
            if (ACTION == LOAD_MORE) {
                PAGE_NUM++;
            } else {
                //for pull to refresh
                if (isShowLoader)
                    showProgress();
                PAGE_NUM = 1;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMyOrderViewModel.getOrderData(PAGE_NUM);
                }
            }, 500);
        } else {
            //if not internet connection during swipe or load more than show pop up than no internet layout
            if (mFragmentMyOrderBinding.swipeRefreshLayout.isRefreshing()
                    || mFragmentMyOrderBinding.recycleview.isLoadingData()) {
                AlertUtils.showAlertMessage(getActivity(), 0, null, null);
            } else {
                mMyOrderViewModel.setNoInternet(true);
            }

            hideLoader();
            mFragmentMyOrderBinding.recycleview.loadMoreComplete(false);
        }
    }

    public void initializeData() {


        mFragmentMyOrderBinding.recycleview.setHasFixedSize(true);
        mFragmentMyOrderBinding.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentMyOrderBinding.recycleview.setMotionEventSplittingEnabled(false);
        mFragmentMyOrderBinding.recycleview.loadMoreComplete(true);
        mFragmentMyOrderBinding.recycleview.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentMyOrderBinding.recycleview.setOnLoadMoreListener(mOnLoadMoreListener);
        mFragmentMyOrderBinding.recycleview.setPageSize(20);

        Utility.applyTypeFace(getActivity(), mFragmentMyOrderBinding.baseLayout);

        mFragmentMyOrderBinding.linearEmpty.setVisibility(View.GONE);

        mList = new ArrayList<>();
    }

    @Override
    public void onItemClick(int position, DataMyOrder responeData) {
        ((MainActivity) getActivity()).pushFragments(OrderDetailFragment.newInstance(responeData), true, true);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onApiSuccess() {
        hideLoader();

    }

    @Override
    public void onApiError(ApiError error) {
        hideLoader();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void swipeRefresh() {
        mFragmentMyOrderBinding.swipeRefreshLayout.setRefreshing(true);
        ACTION = LOAD_LIST;
        mFragmentMyOrderBinding.recycleview.setIsLoadingMore(true);
        checkInternet(false);
    }

    @Override
    public void getOrderList(RMyOrderList orderList, List<DataMyOrder> listRespones) {

        switch (ACTION) {
            case LOAD_LIST:

                totalOrderList = orderList.getCount();
                mList.clear();
                mList.addAll(listRespones);
                listAdapter = new MyOrderListAdapter(getActivity(), mList, this);
                mFragmentMyOrderBinding.recycleview.setAdapter(listAdapter);
                mFragmentMyOrderBinding.swipeRefreshLayout.setRefreshing(false);

                //issue of space in bottom of recycleview in last item when total item size small than 20;
                if (totalOrderList <= 20)
                    mFragmentMyOrderBinding.recycleview.setIsLoadingMore(false);

                break;
            case LOAD_MORE:

                mList.addAll(listRespones);
                listAdapter.notifyDataSetChanged();
                mFragmentMyOrderBinding.recycleview.loadMoreComplete(false);

                break;
        }

        mMyOrderViewModel.setListVisibility(mList.isEmpty() ? false : true);
    }


    public void hideLoader() {
        if (mFragmentMyOrderBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentMyOrderBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
            mFragmentMyOrderBinding.recycleview.loadMoreComplete(false);
        }
    }
}
