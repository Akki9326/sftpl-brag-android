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
import com.pulse.brag.adapters.CollectionListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.respones.CategoryListRespone;
import com.pulse.brag.pojo.respones.ImagePagerRespone;
import com.pulse.brag.views.CustomViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            Utility.showAlertMessage(getActivity(), 0);
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


        List<ImagePagerRespone> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerRespone("https://marketplace.canva.com/MAB-l9EMG4I/1/0/thumbnail_large/canva-light-blue-beach-horizon-cool-desktop-wallpaper-MAB-l9EMG4I.jpg", ""));
        imagePagerResponeList.add(new ImagePagerRespone("http://kingfisher.scene7.com/is/image/Kingfisher/5011583143836_04i?crop=15,15,1981,1398&anchor=1005,714&anchor=1007,1007&wid=600", ""));
        imagePagerResponeList.add(new ImagePagerRespone("https://wallpaperscraft.com/image/usa_new_york_top_view_skyscrapers_112139_602x339.jpg", ""));
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn01.androidauthority.net/wp-content/uploads/2015/11/00-best-backgrounds-and-wallpaper-apps-for-android.jpg", ""));

        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mPagerIndicator.setViewPager(mViewPager);

        List<CategoryListRespone> collectionListRespones = new ArrayList<>();
        collectionListRespones.add(new CategoryListRespone("", "Classic Bralet", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature1.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListRespone("", "Racerback Bral", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature2.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListRespone("", "Cage Back Bral", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListRespone("", "Mesh Racerback", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListRespone("", "Classic Bikini", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature5.jpg?9636438770338163967"));
        collectionListRespones.add(new CategoryListRespone("", "Classic Bikini", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature6.jpg?9636438770338163967"));


        mRecyclerView.setAdapter(new CollectionListAdapter(getActivity(), collectionListRespones, this));

        mRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onItemClick(int position) {

        ((BaseActivity) getActivity()).pushFragments(new ProductionListFragment(), true, true);
    }
}
