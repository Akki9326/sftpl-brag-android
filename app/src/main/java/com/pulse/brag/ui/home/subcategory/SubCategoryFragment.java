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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    List<CategoryListResponseData.CategoryList> mCategoryList;
    List<ImagePagerResponse> imagePagerResponeList;

    CategoryListAdapter adapter;

    @Inject
    SubCategoryViewModel categoryViewModel;

    FragmentSubCategoryBinding mFragmentSubCategoryBinding;


    public static SubCategoryFragment newInstance(String url, List<CategoryListResponseData.CategoryList> list) {

        Bundle args = new Bundle();
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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void swipeRefresh() {
        mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet(false);
    }

    @Override
    public void setCategoryList(List<CategoryListResponseData.CategoryList> list) {

        if (list != null && list.size() > 0) {
            categoryViewModel.setNoResult(false);
            mCategoryList.clear();
            mCategoryList.addAll(list);
            showData();
        } else {
            categoryViewModel.setNoResult(true);
        }

    }

    @Override
    public void onNoData() {
        categoryViewModel.setNoResult(true);
    }

    public void hideProgressBar() {
        if (mFragmentSubCategoryBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
