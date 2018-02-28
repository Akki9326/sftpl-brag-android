package com.pulse.brag.ui.home.category;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.CategoryListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentCategoryBinding;
import com.pulse.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.data.model.datas.CategoryListResponseData;
import com.pulse.brag.data.model.response.ImagePagerResponse;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.ui.home.subcategory.SubCategoryFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryFragment extends CoreFragment<FragmentCategoryBinding, CategoryViewModel> implements CategoryNavigator, IOnItemClickListener {

    @Inject
    CategoryViewModel categoryViewModel;

    FragmentCategoryBinding mFragmentCategoryBinding;

    List<CategoryListResponseData> mCategoryList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel.setNavigator(this);
    }


    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentCategoryBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentCategoryBinding.baseLayout);

        mFragmentCategoryBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragmentCategoryBinding.recycleView.setHasFixedSize(true);
        mFragmentCategoryBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentCategoryBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));


        checkInternet();

    }

    @Override
    public void setUpToolbar() {

    }


    private void checkInternet() {

        if (Utility.isConnection(getContext())) {
            if (!mFragmentCategoryBinding.swipeRefreshLayout.isRefreshing())
                showProgress();
            categoryViewModel.getCategoryData();
        } else {
            hideProgressBar();
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }


    }

    @Override
    public CategoryViewModel getViewModel() {
        return categoryViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onApiSuccess() {

        hideProgressBar();

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));

        mFragmentCategoryBinding.viewPager.setAdapter(new ImagePagerAdapter(getBaseActivity(), imagePagerResponeList));
        mFragmentCategoryBinding.pagerView.setViewPager(mFragmentCategoryBinding.viewPager);


    }

    @Override
    public void onApiError(ApiError error) {
        hideProgressBar();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void swipeRefresh() {
        mFragmentCategoryBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet();
    }

    @Override
    public void getCategoryList(List<CategoryListResponseData> list) {
        mCategoryList = new ArrayList<>();
        mCategoryList.addAll(list);
        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), mCategoryList, this);

        mFragmentCategoryBinding.recycleView.setAdapter(adapter);
        mFragmentCategoryBinding.recycleView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(SubCategoryFragment.newInstance(mCategoryList.get(position).getUrl(), mCategoryList.get(position).getChild()), true, true);

    }

    public void hideProgressBar() {
        if (mFragmentCategoryBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
