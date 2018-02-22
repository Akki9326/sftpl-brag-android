package com.pulse.brag.ui.myorder;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.MyOrderList1Adapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentMyOrderBinding;
import com.pulse.brag.pojo.datas.MyOrderListResponeData;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class MyOrderFragment extends CoreFragment<FragmentMyOrderBinding, MyOrderViewModel> implements MyOrderNavigator, MyOrderList1Adapter.OnItemClick {


    List<MyOrderListResponeData> mList;
    @Inject
    MyOrderViewModel mMyOrderViewModel;
    FragmentMyOrderBinding mFragmentMyOrderBinding;

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_my_order, container, false);
            initializeData();
            checkInternet();
        }
        return mView;
    }*/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyOrderViewModel.setNavigator(this);
    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
            showProgress();
            mMyOrderViewModel.getOrderData();
        } else {
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
        ((MainActivity) getActivity()).showToolbar(true, false, false, getString(R.string.toolbar_my_order));
    }

    @Override
    public MyOrderViewModel getViewModel() {
        return mMyOrderViewModel;
    }


    @Override
    public int getBindingVariable() {
        return com.pulse.brag.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    public void initializeData() {


        mFragmentMyOrderBinding.recycleview.setHasFixedSize(true);
        mFragmentMyOrderBinding.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        Utility.applyTypeFace(getActivity(), mFragmentMyOrderBinding.baseLayout);


    }

    public void showData() {

        mList = new ArrayList<>();
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "" + getString(R.string.text_s),
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "10", "5000"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "100", "5000"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "100", "50000"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0135_2_large.jpg?v=1495697256",
                "1", "500"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new MyOrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));


        mFragmentMyOrderBinding.recycleview.setAdapter(new MyOrderList1Adapter(mList, this));

        if (mList.isEmpty()) {
            mFragmentMyOrderBinding.linearEmpty.setVisibility(View.VISIBLE);
            mFragmentMyOrderBinding.linearOrderList.setVisibility(View.GONE);
        } else {
            mFragmentMyOrderBinding.linearOrderList.setVisibility(View.VISIBLE);
            mFragmentMyOrderBinding.linearEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position, MyOrderListResponeData responeData) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        showData();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }
}
