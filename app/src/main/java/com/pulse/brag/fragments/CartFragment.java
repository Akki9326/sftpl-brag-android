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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.CartListAdapter;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.datas.CartListResponeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 07-11-2017.
 */


public class CartFragment extends BaseFragment implements BaseInterface {

    View mView;
    RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cart, container, false);
            initializeData();
            setListeners();
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
        ((BaseActivity) getActivity()).showToolbar(true, false, false, getResources().getString(R.string.toolbar_label_cart));
    }

    @Override
    public void initializeData() {

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setMotionEventSplittingEnabled(false);

        Utility.applyTypeFace(getActivity(), (RelativeLayout) mView.findViewById(R.id.base_layout));

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void showData() {

        List<CartListResponeData> mList = new ArrayList<>();
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Product Name", "L", "#ffffff", "500", "1"));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#ffffff", "500", "1"));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0135_2_large.jpg?v=1495697256",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#ffffff", "500", "1"));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/yellow-caged_front_large.jpg?v=1480569528",
                "Plunge Neck Cage Back T-shirt Bralette - Yellow", "L", "#ffffff", "500", "1"));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/pink-printed-cage_front_large.jpg?v=1482476302",
                "Plunge Neck Cage Back T-shirt Bralette - Pink", "L", "#ffffff", "500", "1"));


        mRecyclerView.setAdapter(new CartListAdapter(getActivity(), mList));


    }
}
