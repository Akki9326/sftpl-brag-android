package com.pulse.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.ui.home.adapter.CategoryListAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentSubCategoryBinding;
import com.pulse.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.data.model.datas.CategoryListResponseData;
import com.pulse.brag.data.model.response.ImagePagerResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 29-01-2018.
 */


public class SubCategoryFragment extends CoreFragment<FragmentSubCategoryBinding, SubCategoryViewModel> implements SubCategoryNavigator, IOnItemClickListener {

    @Inject
    SubCategoryViewModel categoryViewModel;
    FragmentSubCategoryBinding mFragmentSubCategoryBinding;

    List<CategoryListResponseData.Category> mCategoryList;
    List<ImagePagerResponse> imagePagerResponeList;
    String mCategory;

    CategoryListAdapter adapter;


    public static SubCategoryFragment newInstance(String category, String url, List<CategoryListResponseData.Category> list) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_NAME, category);
        args.putString(Constants.BUNDLE_IMAGE_URL, url);
        args.putParcelableArrayList(Constants.BUNDLE_CATEGORY_LIST, (ArrayList<? extends Parcelable>) list);
        SubCategoryFragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mCategoryList = Collections.emptyList();
        imagePagerResponeList = Collections.emptyList();
        if (getArguments().containsKey(Constants.BUNDLE_CATEGORY_LIST)) {
            mCategoryList = getArguments().getParcelableArrayList(Constants.BUNDLE_CATEGORY_LIST);
        }
        if (getArguments().containsKey(Constants.BUNDLE_IMAGE_URL)) {
            categoryViewModel.setProductImg(getArguments().getString(Constants.BUNDLE_IMAGE_URL));
        }
        if (getArguments().containsKey(Constants.BUNDLE_CATEGORY_NAME)) {
            mCategory = getArguments().getString(Constants.BUNDLE_CATEGORY_NAME);
        }

    }

    @Override
    public void afterViewCreated() {
        mFragmentSubCategoryBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mFragmentSubCategoryBinding.baseLayout);

        categoryViewModel.setNoResult(false);
        categoryViewModel.setNoInternet(false);

        mFragmentSubCategoryBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragmentSubCategoryBinding.recycleView.setHasFixedSize(true);
        mFragmentSubCategoryBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentSubCategoryBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));
        mFragmentSubCategoryBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternet(true);
            }
        });


        showData();
    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, true, mCategory == null ? getString(R.string.toolbar_label_sub_category) : mCategory);

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

    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(ProductListFragment.newInstance(mCategory, mCategoryList.get(position).getOptionName()), true, true);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setUpToolbar();
        }
    }

    private void checkInternet(boolean showProgress) {

        if (Utility.isConnection(getActivity())) {
            categoryViewModel.setNoInternet(false);
            if (showProgress)
                showProgress();
            categoryViewModel.getSubCategoryData();
        } else {
            categoryViewModel.setNoInternet(true);
            hideProgressBar();
        }

    }


    private void showData() {
        adapter = new CategoryListAdapter(getContext(), mCategoryList, this);
        mFragmentSubCategoryBinding.recycleView.setAdapter(adapter);
        mFragmentSubCategoryBinding.recycleView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onApiSuccess() {
        hideProgressBar();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgressBar();
        if (error.getHttpCode() == 0 && error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            categoryViewModel.setNoInternet(true);
            return;
        }
        categoryViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
    }

    @Override
    public void swipeRefresh() {
        //// TODO: 3/12/2018 remove comment for swipe refresh
        mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        /*mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet(false);*/
    }

    @Override
    public void onNoData() {
        categoryViewModel.setNoResult(true);
    }

    @Override
    public void setCategoryList(List<CategoryListResponseData.Category> list) {
        categoryViewModel.setNoResult(false);
        mCategoryList.clear();
        mCategoryList.addAll(list);
        showData();
    }

    public void hideProgressBar() {
        if (mFragmentSubCategoryBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
