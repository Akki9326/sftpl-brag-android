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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pulse.brag.R;
import com.pulse.brag.adapters.CollectionListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.callback.BaseInterface;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.pojo.datas.CollectionListResponeData;
import com.pulse.brag.pojo.response.CollectionListResponse;
import com.pulse.brag.pojo.response.ImagePagerResponse;
import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.CustomViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class CollectionFragment extends Fragment implements BaseInterface, IOnItemClickListener {

    View mView;
    ViewPager mViewPager;
    CustomViewPagerIndicator mPagerIndicator;
    RecyclerView mRecyclerView;
    CoordinatorLayout mCoordinatorLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mLinearDetail;

    List<CollectionListResponeData> mCollectionList;

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

    }

    @Override
    public void initializeData() {

        mCoordinatorLayout =  mView.findViewById(R.id.base_layout);
        mViewPager =  mView.findViewById(R.id.view_pager);
        mPagerIndicator = mView.findViewById(R.id.pager_view);

        mRecyclerView =  mView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setMotionEventSplittingEnabled(false);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mSwipeRefreshLayout =  mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink));
        mLinearDetail = mView.findViewById(R.id.linear_detail);
        Utility.applyTypeFace(getContext(), mCoordinatorLayout);

    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
            GetCollectionAPI();
        } else {
            //Utility.showAlertMessage(getActivity(), 0, null);
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }

    }

    private void GetCollectionAPI() {
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<CollectionListResponse> getCategoryList = ApiClient.getInstance(getContext()).getApiResp().getCollectionProduct("home/get/2");
        getCategoryList.enqueue(new Callback<CollectionListResponse>() {
            @Override
            public void onResponse(Call<CollectionListResponse> call, Response<CollectionListResponse> response) {
                if (response.isSuccessful()) {
                    CollectionListResponse data = response.body();
                    if (data.isStatus()) {
                        mCollectionList = new ArrayList<>();
                        mCollectionList.addAll(data.getData());
//                        Collections.sort(mCollectionList);
                        showData();
                    } else {
                        //Utility.showAlertMessage(getContext(), data.getErrorCode(), data.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), data.getErrorCode(), data.getMessage());
                    }
                } else {
                    //Utility.showAlertMessage(getContext(), 1, null);
                    AlertUtils.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<CollectionListResponse> call, Throwable t) {
                //Utility.showAlertMessage(getContext(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });
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


        mRecyclerView.setAdapter(new CollectionListAdapter(getActivity(), mCollectionList, this));
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(new ProductListFragment(), true, true);
    }
}
