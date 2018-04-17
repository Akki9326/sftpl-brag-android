package com.ragtagger.brag.ui.home.subcategory;


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

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.data.model.response.RImagePager;
import com.ragtagger.brag.databinding.FragmentSubCategoryBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.adapter.CategoryListAdapter;
import com.ragtagger.brag.ui.home.product.list.ProductListFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.erecyclerview.GridSpacingItemDecoration;

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

    List<DataCategoryList.Category> mCategoryList;
    List<RImagePager> imagePagerResponeList;
    String mCategory;
    String mSizeGuide;

    CategoryListAdapter adapter;


    public static SubCategoryFragment newInstance(String category, String url, List<DataCategoryList.Category> list, String sizeGuide) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_NAME, category);
        args.putString(Constants.BUNDLE_IMAGE_URL, url);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
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

        if (getArguments().containsKey(Constants.BUNDLE_SIZE_GUIDE)) {
            mSizeGuide = getArguments().getString(Constants.BUNDLE_SIZE_GUIDE);
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
        if (mActivity != null && mActivity instanceof ToolbarActivity)
            ((ToolbarActivity) mActivity).showToolbar(true, false, true, mCategory == null ? getString(R.string.toolbar_label_sub_category) : mCategory);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setUpToolbar();
        }
    }

    private void checkInternet(boolean showProgress) {
        if (mActivity != null)
            if (Utility.isConnection(mActivity)) {
                categoryViewModel.setNoInternet(false);
                if (showProgress)
                    showProgress();
                categoryViewModel.callGetSubCategoryDataApi();
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

    public void hideProgressBar() {
        if (mFragmentSubCategoryBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity) mActivity).pushFragments(ProductListFragment.newInstance(mCategory, mCategoryList.get(position).getOptionName(), mSizeGuide), true, true);
    }

    @Override
    public void performSwipeRefresh() {
        mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNoData() {
        categoryViewModel.setNoResult(true);
    }

    @Override
    public void setCategoryList(List<DataCategoryList.Category> list) {
        categoryViewModel.setNoResult(false);
        mCategoryList.clear();
        mCategoryList.addAll(list);
        showData();
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
        AlertUtils.showAlertMessage(mActivity, error.getHttpCode(), error.getMessage(), null);
    }
}
