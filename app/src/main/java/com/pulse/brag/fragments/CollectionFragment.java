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
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.CollectionListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.CollectionListResponse;
import com.pulse.brag.pojo.response.ImagePagerResponse;
import com.pulse.brag.views.CustomViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class CollectionFragment extends Fragment implements BaseInterface, OnItemClickListener {

    View mView;
    ViewPager mViewPager;
    CustomViewPagerIndicator mPagerIndicator;
    RecyclerView mRecyclerView;
    CoordinatorLayout mCoordinatorLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mLinearDetail;

    public static CollectionFragment newInstance() {

        Bundle args = new Bundle();

        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_collection, container, false);
            initializeData();
            setListeners();
            checkInternet();
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
//        ((BaseActivity) getActivity()).showToolbar(false, true, true);

    }

    @Override
    public void initializeData() {

        mCoordinatorLayout = (CoordinatorLayout) mView.findViewById(R.id.base_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mPagerIndicator = (CustomViewPagerIndicator) mView.findViewById(R.id.pager_view);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setMotionEventSplittingEnabled(false);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mLinearDetail = (LinearLayout) mView.findViewById(R.id.linear_detail);
        Utility.applyTypeFace(getContext(), mCoordinatorLayout);

    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
//            GetCollectionAPI();
            showData();
        } else {
            Utility.showAlertMessage(getActivity(), 0);
        }

    }

    @Override
    public void setListeners() {

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                checkInternet();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showData() {

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Blog-BBF_grande.jpg?v=1511844043", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/15871545_1859312094353423_2712560930954384886_n_726da0e0-cc2f-4c97-bf23-edb240420e7b_grande.jpg?v=1492607373", ""));

        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mPagerIndicator.setViewPager(mViewPager);

        List<CollectionListResponse> collectionListRespones = new ArrayList<>();
        collectionListRespones.add(new CollectionListResponse("", "Classic Bralet", "http://cdn.shopify.com/s/files/1/1629/9535/products/0_6a00a44b-22a8-4872-9ca4-16552a32188f_large.jpg?v=1490696913"));
        collectionListRespones.add(new CollectionListResponse("", "Racerback Bral Racerback Bral Racerback Bral Racerback Bral Racerback Bral", "http://cdn.shopify.com/s/files/1/1629/9535/products/0_large.jpg?v=1490696473"));
        collectionListRespones.add(new CollectionListResponse("", "Cage Back Bral", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg?9636438770338163967"));
        collectionListRespones.add(new CollectionListResponse("", "Mesh Racerback", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg?9636438770338163967"));
        collectionListRespones.add(new CollectionListResponse("", "Classic Bikini", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature5.jpg?9636438770338163967"));
        collectionListRespones.add(new CollectionListResponse("", "Classic Bikini", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature6.jpg?9636438770338163967"));


        mRecyclerView.setAdapter(new CollectionListAdapter(getActivity(), collectionListRespones, this));
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(int position) {
        ((BaseActivity) getActivity()).pushFragments(new ProductionListFragment(), true, true);
    }
}
