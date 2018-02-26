package com.pulse.brag.ui.home.subcategory;


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
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.CategoryListAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentSubCategoryBinding;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.datas.CategoryListResponseData;
import com.pulse.brag.pojo.response.ImagePagerResponse;
import com.pulse.brag.views.CustomViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 29-01-2018.
 */


public class SubCategoryFragment extends CoreFragment<FragmentSubCategoryBinding, SubCategoryViewModel> implements SubCategoryNavigator, OnItemClickListener {


    List<CategoryListResponseData> mCategoryList;


    @Inject
    SubCategoryViewModel categoryViewModel;

    FragmentSubCategoryBinding mFragmentSubCategoryBinding;


    public static SubCategoryFragment newInstance(String url, String title) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_IMAGE_URL, url);
        args.putString(Constants.BUNDLE_PRODUCT_NAME, title);
        SubCategoryFragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel.setNavigator(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void checkInternet() {

        if (Utility.isConnection(getActivity())) {
            if (!mFragmentSubCategoryBinding.swipeRefreshLayout.isRefreshing())
                showProgress();
            categoryViewModel.getSubCategoryData();
        } else {
            hideProgressBar();
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }

    }


    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {

        mFragmentSubCategoryBinding = getViewDataBinding();

        mFragmentSubCategoryBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragmentSubCategoryBinding.recycleView.setHasFixedSize(true);
        mFragmentSubCategoryBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentSubCategoryBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));

        Utility.applyTypeFace(getContext(), mFragmentSubCategoryBinding.baseLayout);

        mCategoryList = new ArrayList<>();


        checkInternet();
    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_sub_category));

    }

    @Override
    public SubCategoryViewModel getViewModel() {
        return categoryViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sub_category;
    }

   /* @Override
    public void setToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_sub_category));

    }*/

   /* @Override
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

        Utility.applyTypeFace(getContext(), mFragmentCategoryBinding.baseLayout);

        mCategoryList = new ArrayList<>();
    }*/

    /*@Override
    public void setListeners() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternet();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }*/

  /*  @Override
    public void showData() {

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        if (getArguments().containsKey(Constants.BUNDLE_IMAGE_URL)) {
            imagePagerResponeList.add(new ImagePagerResponse(getArguments().getString(Constants.BUNDLE_IMAGE_URL), ""));
        }


        if ((getArguments().getString(Constants.BUNDLE_PRODUCT_NAME)).equalsIgnoreCase("BRALETTES")) {
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature1.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "CAGE BACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature2.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "RACE BACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "MESH RACEBACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature4.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "TRIANGLE MESH BACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature5.jpg", 0));
        }

        if ((getArguments().getString(Constants.BUNDLE_PRODUCT_NAME)).equalsIgnoreCase("SPORTS BRA")) {
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC CROSS BACK SPORTS", "http://cdn.shopify.com/s/files/1/1629/9535/products/SBD02BU01-side_large.jpg?v=1516609951", 0));
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC RACERBACK", "http://cdn.shopify.com/s/files/1/1629/9535/products/SBD07PK02-style_large.jpg?v=1516609953", 0));
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC RACERBACK YOGA BRA", "http://cdn.shopify.com/s/files/1/1629/9535/products/BLC07BU01-front_large.jpg?v=1516609950", 0));
            mCategoryList.add(new CategoryListResponseData("", "PLUNGE NECK MESH BACK", "http://cdn.shopify.com/s/files/1/1629/9535/products/SBD05BU01-front_large.jpg?v=1516609952", 0));
        }
        if ((getArguments().getString(Constants.BUNDLE_PRODUCT_NAME)).equalsIgnoreCase("PANTIES"))
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC BIKINI PANTY", "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0211_63ad3b1f-040d-460a-887e-211248bd58a0_large.jpg?v=1516609959", 0));


//        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", ""));
        mPagerAdapter = new ImagePagerAdapter(getActivity(), imagePagerResponeList);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerIndicator.setViewPager(mViewPager);

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), mCategoryList, this);
        mRecyclerView.setAdapter(mCategoryAdapter);

        mRecyclerView.setNestedScrollingEnabled(false);
    }*/

   /* private void GetCategoryAPI() {
        showProgressDialog();
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<CategoryListResponse> getCategoryList = ApiClient.getInstance(getContext()).getApiResp().getCategoryProduct("home/get/1");
        getCategoryList.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {

                    CategoryListResponse data = response.body();
                    if (data.isStatus()) {
                        mCategoryList = new ArrayList<>();


//                        mCategoryList.addAll(data.getData());
//                        mCategoryList.remove(0);
//                        mCategoryList.remove(1);
//                        Collections.sort(mCategoryList);
                        showData();
//                        } else {
//                            Utility.showAlertMessage(getContext(), data.getErrorCode(), data.getMessage());
//                        }
                    } else {
                        //Utility.showAlertMessage(getContext(), 1, null);
                        AlertUtils.showAlertMessage(getContext(), 1, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getContext(), t);
                AlertUtils.showAlertMessage(getContext(), t);
            }
        });
    }*/

    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(new ProductListFragment(), true, true);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setUpToolbar();
        }
    }


    @Override
    public void onApiSuccess() {
        hideProgressBar();
        mCategoryList = new ArrayList<>();
        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        if (getArguments().containsKey(Constants.BUNDLE_IMAGE_URL)) {
            imagePagerResponeList.add(new ImagePagerResponse(getArguments().getString(Constants.BUNDLE_IMAGE_URL), ""));
        }


        if ((getArguments().getString(Constants.BUNDLE_PRODUCT_NAME)).equalsIgnoreCase("BRALETTES")) {
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature1.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "CAGE BACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature2.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "RACE BACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature3.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "MESH RACEBACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature4.jpg", 0));
            mCategoryList.add(new CategoryListResponseData("", "TRIANGLE MESH BACK", "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature5.jpg", 0));
        }

        if ((getArguments().getString(Constants.BUNDLE_PRODUCT_NAME)).equalsIgnoreCase("SPORTS BRA")) {
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC CROSS BACK SPORTS", "http://cdn.shopify.com/s/files/1/1629/9535/products/SBD02BU01-side_large.jpg?v=1516609951", 0));
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC RACERBACK", "http://cdn.shopify.com/s/files/1/1629/9535/products/SBD07PK02-style_large.jpg?v=1516609953", 0));
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC RACERBACK YOGA BRA", "http://cdn.shopify.com/s/files/1/1629/9535/products/BLC07BU01-front_large.jpg?v=1516609950", 0));
            mCategoryList.add(new CategoryListResponseData("", "PLUNGE NECK MESH BACK", "http://cdn.shopify.com/s/files/1/1629/9535/products/SBD05BU01-front_large.jpg?v=1516609952", 0));
        }
        if ((getArguments().getString(Constants.BUNDLE_PRODUCT_NAME)).equalsIgnoreCase("PANTIES"))
            mCategoryList.add(new CategoryListResponseData("", "CLASSIC BIKINI PANTY", "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0211_63ad3b1f-040d-460a-887e-211248bd58a0_large.jpg?v=1516609959", 0));


        mFragmentSubCategoryBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mFragmentSubCategoryBinding.pagerView.setViewPager(mFragmentSubCategoryBinding.viewPager);

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), mCategoryList, this);
        mFragmentSubCategoryBinding.recycleView.setAdapter(adapter);

        mFragmentSubCategoryBinding.recycleView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgressBar();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void swipeRefresh() {
        mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet();
    }

    public void hideProgressBar() {
        if (mFragmentSubCategoryBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
