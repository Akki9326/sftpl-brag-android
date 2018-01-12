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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.R;
import com.pulse.brag.adapters.CategoryListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.ImagePagerResponse;
import com.pulse.brag.views.CustomViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class CategoryFragment extends Fragment implements BaseInterface, OnItemClickListener {


    View mView;
    ViewPager mViewPager;
    CustomViewPagerIndicator mPagerIndicator;
    RecyclerView mRecyclerView;
    CoordinatorLayout mCoordinatorLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mLinearDetail;

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_category, container, false);
            initializeData();
            setListeners();
            checkInternet();
        }
        return mView;
    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
//            GetCollectionAPI();
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
//        ((BaseActivity) getActivity()).showToolbar(false, true, true);
    }

    @Override
    public void initializeData() {

        mCoordinatorLayout = (CoordinatorLayout) mView.findViewById(R.id.base_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mPagerIndicator = (CustomViewPagerIndicator) mView.findViewById(R.id.pager_view);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink));
        mLinearDetail = (LinearLayout) mView.findViewById(R.id.linear_detail);
        Utility.applyTypeFace(getContext(), mCoordinatorLayout);

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
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", ""));
//        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
//        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mPagerIndicator.setViewPager(mViewPager);



        List<CategoryListResponse> collectionListRespones = new ArrayList<>();
        collectionListRespones.add(new CategoryListResponse("", "Classic Bralet", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature1.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListResponse("", "Racerback Bral", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature2.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListResponse("", "Cage Back Bral", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListResponse("", "Mesh Racerback", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListResponse("", "Classic Bikini", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature5.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListResponse("", "Classic Bikini", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature6.jpg?9636438770338163967"));


        mRecyclerView.setAdapter(new CategoryListAdapter(getActivity(), collectionListRespones, this));

        mRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onItemClick(int position) {

        ((BaseActivity) getActivity()).pushFragments(new ProductionListFragment(), true, true);
    }
}
