package com.pulse.brag.ui.fragments;

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

import com.pulse.brag.ui.activities.BaseActivity;
import com.pulse.brag.R;
import com.pulse.brag.adapters.CategoryListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.datas.CategoryListResponseData;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.ImagePagerResponse;
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

    CategoryListAdapter mCategoryAdapter;
    ImagePagerAdapter mPagerAdapter;
    List<CategoryListResponseData> mCategoryList;


    List<String> mTransitionString;
    List<View> mTransitionView;

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
            GetCategoryAPI();
//            showData();
        } else {
            Utility.showAlertMessage(getActivity(), 0, null);
        }

    }

    private void GetCategoryAPI() {
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<CategoryListResponse> getCategoryList = ApiClient.getInstance(getContext()).getApiResp().getCategoryProduct("home/get/1");
        getCategoryList.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.isSuccessful()) {
                    CategoryListResponse data = response.body();
                    if (data.isStatus()) {
                        mCategoryList = new ArrayList<>();
//                        mCategoryList.addAll(data.getData());
//                        Collections.sort(mCategoryList);

                        mCategoryList.add(new CategoryListResponseData("", "BRALETTES", "https://cdn.shopify.com/s/files/1/1629/9535/products/Chandini_SLIDE_Pattern_B_BRAG-Optical59961_grande.jpg?v=1516609967", 0));
                        mCategoryList.add(new CategoryListResponseData("", "SPORTS BRA", "https://cdn.shopify.com/s/files/1/1629/9535/files/Sport-Inside-Banner.jpg?17088418355138553923", 0));
                        mCategoryList.add(new CategoryListResponseData("", "PANTIES", "http://cdn.shopify.com/s/files/1/1629/9535/products/2_front_7b6ad7b4-2320-420b-badd-07087487111f_large.jpg?v=1516609968", 0));

                        showData();
                    } else {
                        Utility.showAlertMessage(getContext(), data.getErrorCode(), data.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getContext(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                Utility.showAlertMessage(getContext(), t);
            }
        });
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

        mCoordinatorLayout = mView.findViewById(R.id.base_layout);
        mViewPager = mView.findViewById(R.id.view_pager);
        mPagerIndicator = mView.findViewById(R.id.pager_view);
        mRecyclerView = mView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));
        mSwipeRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink));
        mLinearDetail = mView.findViewById(R.id.linear_detail);

        Utility.applyTypeFace(getContext(), mCoordinatorLayout);

        mCategoryList = new ArrayList<>();

        mTransitionString = new ArrayList<>();
        mTransitionView = new ArrayList<>();
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
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));

        mPagerAdapter = new ImagePagerAdapter(getActivity(), imagePagerResponeList);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerIndicator.setViewPager(mViewPager);

        mCategoryAdapter = new CategoryListAdapter(getActivity(), mCategoryList, this);
        mRecyclerView.setAdapter(mCategoryAdapter);

        mRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onItemClick(int position) {

//        ((BaseActivity) getActivity()).pushFragments(new ProductionListFragment(), true, true);
        ((BaseActivity) getActivity()).pushFragmentsAdd(SubCategotyFragment.newInstance(mCategoryList.get(position).getUrl(), mCategoryList.get(position).getOptionName()), true, true);


    }


}
