package com.pulse.brag.ui.collection;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.model.datas.CategoryListResponseData;
import com.pulse.brag.ui.collection.adapter.CollectionListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.databinding.FragmentCollectionBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.data.model.response.ImagePagerResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class CollectionFragment extends CoreFragment<FragmentCollectionBinding, CollectionViewModel> implements CollectionNavigator, IOnItemClickListener, ImagePagerAdapter.IOnImagePageClickListener {

    @Inject
    CollectionViewModel mCollectionViewModel;

    FragmentCollectionBinding mFragmentCollectionBinding;

    List<CategoryListResponseData.Category> mCollectionList;
    List<ImagePagerResponse> mBannerList;

    CollectionListAdapter mAdapter;

    public static CollectionFragment newInstance() {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollectionViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mCollectionList = new ArrayList<>();
        mAdapter = new CollectionListAdapter(getActivity(), mCollectionList, this);
    }

    @Override
    public void afterViewCreated() {
        mFragmentCollectionBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentCollectionBinding.baseLayout);

        mCollectionViewModel.setNoResult(false);
        mCollectionViewModel.setNoInternet(false);
        mCollectionViewModel.setIsBannerAvail(true);

        mFragmentCollectionBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternet(true);
            }
        });

        mFragmentCollectionBinding.recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentCollectionBinding.recycleView.setHasFixedSize(true);
        mFragmentCollectionBinding.recycleView.setItemAnimator(new DefaultItemAnimator());
        mFragmentCollectionBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentCollectionBinding.recycleView.setAdapter(mAdapter);
        mFragmentCollectionBinding.recycleView.setNestedScrollingEnabled(false);
        checkInternet(true);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public CollectionViewModel getViewModel() {
        return mCollectionViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collection;
    }

    private void checkInternet(boolean showProgress) {
        if (Utility.isConnection(getActivity())) {
            mCollectionViewModel.setNoInternet(false);
            if (showProgress)
                showProgress();
            mCollectionViewModel.getCollectionList();
        } else {
            mCollectionViewModel.setNoInternet(true);
            hideProgressBar();
        }

    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(ProductListFragment.newInstance(mCollectionList.get(position).getOptionName()), true, true);
    }

    @Override
    public void onImagePageClick(int pos, ImagePagerResponse item) {

    }

    @Override
    public void onApiSuccess() {
        hideProgressBar();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgressBar();
        if (error.getHttpCode() == 0 && error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            mCollectionViewModel.setNoInternet(true);
            return;
        }
        mCollectionViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
    }

    @Override
    public void swipeRefresh() {
        mFragmentCollectionBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet(false);
    }

    @Override
    public void onNoData() {
        mCollectionViewModel.setNoResult(true);
    }

    @Override
    public void setCategoryList(List<CategoryListResponseData.Category> list) {
        mCollectionList.clear();
        mCollectionList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBanner(List<CategoryListResponseData.Banners> list) {
        if (list != null && list.size() > 0) {
            mCollectionViewModel.setIsBannerAvail(true);
            mBannerList = new ArrayList<>();
            for (CategoryListResponseData.Banners item : list) {
                mBannerList.add(new ImagePagerResponse(item.getUrl(), item.getId()));
            }
            mFragmentCollectionBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), mBannerList, this));
            mFragmentCollectionBinding.pagerView.setViewPager(mFragmentCollectionBinding.viewPager);
        } else {
            mCollectionViewModel.setIsBannerAvail(false);
        }
    }

    public void hideProgressBar() {
        if (mFragmentCollectionBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentCollectionBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
