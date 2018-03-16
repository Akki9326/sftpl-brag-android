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
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.BragApp;
import com.pulse.brag.R;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.ui.home.HomeFragment;
import com.pulse.brag.ui.home.adapter.CategoryListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentCategoryBinding;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.data.model.datas.DataCategoryList;
import com.pulse.brag.data.model.response.RImagePager;
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


public class CategoryFragment extends CoreFragment<FragmentCategoryBinding, CategoryViewModel> implements CategoryNavigator, IOnItemClickListener, ImagePagerAdapter.IOnImagePageClickListener {

    @Inject
    CategoryViewModel categoryViewModel;

    FragmentCategoryBinding mFragmentCategoryBinding;

    List<DataCategoryList.Category> mCategoryList;
    List<RImagePager> mBannerList;


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

        categoryViewModel.setNoResult(false);
        categoryViewModel.setNoInternet(false);
        categoryViewModel.setIsBannerAvail(true);

        mFragmentCategoryBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragmentCategoryBinding.recycleView.setHasFixedSize(true);
        mFragmentCategoryBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentCategoryBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));
        mFragmentCategoryBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternet(true);

            }
        });

        checkInternet(true);
    }

    @Override
    public void setUpToolbar() {

    }


    private void checkInternet(boolean showProgress) {
        hideProgressBar();
        if (Utility.isConnection(getContext())) {
            categoryViewModel.setNoInternet(false);
            if (showProgress)
                showProgress();
            categoryViewModel.getCategoryData();
        } else {
            categoryViewModel.setNoInternet(true);
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
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(SubCategoryFragment.newInstance(mCategoryList.get(position).getOptionName(), mCategoryList.get(position).getUrl(), mCategoryList.get(position).getChild(), mCategoryList.get(position).getSizeGuide()), true, true);
    }

    @Override
    public void onImagePageClick(int pos, RImagePager item) {
        //((MainActivity) getActivity()).pushFragments(SubCategoryFragment.newInstance(mCategoryList.get(position).getUrl(), mCategoryList.get(position).getChild()), true, true);
    }

    @Override
    public void onApiSuccess() {
        hideProgressBar();
        ((MainActivity) getActivity()).updateCartNum();
        ((HomeFragment) getParentFragment()).setNotificationBadge();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgressBar();
        if (error.getHttpCode() == 0 && error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            categoryViewModel.setNoInternet(true);
            return;
        }
        categoryViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void swipeRefresh() {
        mFragmentCategoryBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet(false);
    }

    @Override
    public void onNoData() {
        categoryViewModel.setNoResult(true);
    }

    @Override
    public void setCategoryList(List<DataCategoryList.Category> list) {
        for (DataCategoryList.Category category : list)
            BragApp.getInstance().setMapSizeGuide(category.getOptionName(), category.getSizeGuide());

        mCategoryList = new ArrayList<>();
        mCategoryList.addAll(list);
        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), mCategoryList, this);

        mFragmentCategoryBinding.recycleView.setAdapter(adapter);
        mFragmentCategoryBinding.recycleView.setNestedScrollingEnabled(false);
    }

    @Override
    public void setBanner(List<DataCategoryList.Banners> list) {
        if (list != null && list.size() > 0) {
            categoryViewModel.setIsBannerAvail(true);
            mBannerList = new ArrayList<>();
            for (DataCategoryList.Banners item : list) {
                mBannerList.add(new RImagePager(item.getUrl(), item.getId()));
            }
            mFragmentCategoryBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), mBannerList, this));
            mFragmentCategoryBinding.pagerView.setViewPager(mFragmentCategoryBinding.viewPager);
        } else {
            categoryViewModel.setIsBannerAvail(false);
        }
    }


    public void hideProgressBar() {
        if (mFragmentCategoryBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentCategoryBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }


}
