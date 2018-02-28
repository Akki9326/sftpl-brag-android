package com.pulse.brag.ui.order;

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


import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.MyOrderListAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentMyOrderBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.ui.order.orderdetail.OrderDetailFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.data.model.datas.MyOrderListResponeData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class MyOrderListFragment extends CoreFragment<FragmentMyOrderBinding, MyOrderViewModel> implements MyOrderNavigator, MyOrderListAdapter.OnItemClick {


    List<MyOrderListResponeData> mList;
    @Inject
    MyOrderViewModel mMyOrderViewModel;
    FragmentMyOrderBinding mFragmentMyOrderBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyOrderViewModel.setNavigator(this);
    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
            if (!mFragmentMyOrderBinding.swipeRefreshLayout.isRefreshing())
                showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMyOrderViewModel.getOrderData();
                }
            }, 500);
        } else {
            hideLoader();
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentMyOrderBinding = getViewDataBinding();
        initializeData();
        checkInternet();

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

    public void initializeData() {


        mFragmentMyOrderBinding.recycleview.setHasFixedSize(true);
        mFragmentMyOrderBinding.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        Utility.applyTypeFace(getActivity(), mFragmentMyOrderBinding.baseLayout);

        mFragmentMyOrderBinding.linearEmpty.setVisibility(View.GONE);

    }

    public void showData() {

        mList = new ArrayList<>();
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "" + getString(R.string.text_s),
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500", Constants.OrderStatus.ORDERED.ordinal(), 1519117468));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "10", "5000", Constants.OrderStatus.CANCALLED.ordinal(), 1519031068));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "100", "5000", Constants.OrderStatus.DELIVERED.ordinal(), 1518944668));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "100", "50000", Constants.OrderStatus.PACKED.ordinal(), 1518933716));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0135_2_large.jpg?v=1495697256",
                "1", "500", Constants.OrderStatus.SHIPPED.ordinal(), 1518588116));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500", Constants.OrderStatus.APPROVED.ordinal(), 1519117468));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500", Constants.OrderStatus.ORDERED.ordinal()));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500", Constants.OrderStatus.ORDERED.ordinal()));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500", Constants.OrderStatus.ORDERED.ordinal()));


        mFragmentMyOrderBinding.recycleview.setAdapter(new MyOrderListAdapter(getActivity(), mList, this));


        mMyOrderViewModel.setListVisibility(mList.isEmpty() ? false : true);

    }

    @Override
    public void onItemClick(int position, MyOrderListResponeData responeData) {
        ((MainActivity) getActivity()).pushFragments(OrderDetailFragment.newInstance(responeData.getOrder_id(), responeData), true, true);

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
        showData();
    }

    @Override
    public void onApiError(ApiError error) {
        hideLoader();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void swipeRefresh() {
        mFragmentMyOrderBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet();
    }

    public void hideLoader() {
        if (mFragmentMyOrderBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentMyOrderBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
