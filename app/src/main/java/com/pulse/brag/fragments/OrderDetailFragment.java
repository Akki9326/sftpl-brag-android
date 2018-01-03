package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.OrderDetailsAdapter;
import com.pulse.brag.erecyclerview.ERecyclerView;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.datas.OrderListResponeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class OrderDetailFragment extends BaseFragment implements BaseInterface {

    View mView;
    ERecyclerView mRecyclerView;
    LinearLayout mLinearList, mLinearError;

    List<OrderListResponeData> mList;

    public static OrderDetailFragment newInstance() {

        Bundle args = new Bundle();

        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_order_detail, container, false);
            initializeData();
            setListeners();
            checkInternet();
        }
        return mView;
    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
            showData();
        } else {
            Utility.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {

        ((BaseActivity) getActivity()).showToolbar(true, false, false, getString(R.string.toolbar_my_order));
    }

    @Override
    public void initializeData() {

        mRecyclerView = mView.findViewById(R.id.recycleview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLinearList = mView.findViewById(R.id.linear_order_list);
        mLinearError = mView.findViewById(R.id.linear_empty);

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void showData() {

        mList = new ArrayList<>();
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "" + getString(R.string.text_s),
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "10", "5000"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "100", "5000"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "100", "50000"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0135_2_large.jpg?v=1495697256",
                "1", "500"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));
        mList.add(new OrderListResponeData("1", "" + Utility.randomString(10), "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "1", "500"));


        mRecyclerView.setAdapter(new OrderDetailsAdapter(getActivity(), mList));

        if (mList.isEmpty()) {
            mLinearError.setVisibility(View.VISIBLE);
            mLinearList.setVisibility(View.GONE);
        } else {
            mLinearList.setVisibility(View.VISIBLE);
            mLinearError.setVisibility(View.GONE);
        }
    }
}
