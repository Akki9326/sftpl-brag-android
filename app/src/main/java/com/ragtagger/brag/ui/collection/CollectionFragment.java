package com.ragtagger.brag.ui.collection;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.ui.collection.adapter.CollectionListAdapter;
import com.ragtagger.brag.adapters.ImagePagerAdapter;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.databinding.FragmentCollectionBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.HomeFragment;
import com.ragtagger.brag.ui.home.product.list.ProductListFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.data.model.response.RImagePager;

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

    List<DataCategoryList.Category> mCollectionList;
    List<RImagePager> mBannerList;
    CollectionListAdapter mAdapter;

    public static CollectionFragment getInstance() {
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
        mCollectionViewModel.setIsListAvail(true);

        mFragmentCollectionBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternetAndCallApi(true);
            }
        });

        mFragmentCollectionBinding.recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentCollectionBinding.recycleView.setHasFixedSize(true);
        mFragmentCollectionBinding.recycleView.setItemAnimator(new DefaultItemAnimator());
        mFragmentCollectionBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentCollectionBinding.recycleView.setAdapter(mAdapter);
        mFragmentCollectionBinding.recycleView.setNestedScrollingEnabled(false);
        checkInternetAndCallApi(true);
    }

    @Override
    public void setUpToolbar() {
        /*if (mActivity != null && mActivity instanceof ToolbarActivity)
            ((ToolbarActivity) mActivity).showToolbar(false, true, true);*/
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

    private void checkInternetAndCallApi(boolean showProgress) {
        if (Utility.isConnection(getActivity())) {
            mCollectionViewModel.setNoInternet(false);
            if (showProgress)
                showProgress();
            mCollectionViewModel.callGetCollectionListApi();
        } else {
            hideProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCollectionViewModel.setNoInternet(true);
                }
            }, 150);


        }

    }

    public void hideProgressBar() {
        if (mFragmentCollectionBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentCollectionBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }

    @Override
    public void onItemClick(int position) {
        if (mActivity != null)
            ((MainActivity) mActivity).pushFragments(ProductListFragment.newInstance(mCollectionList.get(position).getOptionName(), mCollectionList.get(position).getSizeGuide()), true, true);
    }

    @Override
    public void onImagePageClick(int pos, RImagePager item) {

    }

    @Override
    public void performSwipeRefresh() {
        mFragmentCollectionBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternetAndCallApi(false);
    }

    @Override
    public void onNoData() {
        mCollectionViewModel.setNoResult(true);
    }

    @Override
    public void setCategoryList(List<DataCategoryList.Category> list) {
        if (list != null && list.size() > 0) {
            mCollectionViewModel.setIsListAvail(true);
            mCollectionList.clear();
            mCollectionList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            mCollectionViewModel.setIsListAvail(false);
        }
    }

    @Override
    public void setBanner(List<DataCategoryList.Banners> list) {
        if (list != null && list.size() > 0) {
            mCollectionViewModel.setIsBannerAvail(true);
            mBannerList = new ArrayList<>();
            for (DataCategoryList.Banners item : list) {
                mBannerList.add(new RImagePager(item.getUrl(), item.getId()));
            }
            mFragmentCollectionBinding.viewPager.setAdapter(new ImagePagerAdapter(mActivity, mBannerList, this));
            mFragmentCollectionBinding.pagerView.setViewPager(mFragmentCollectionBinding.viewPager);
        } else {
            mCollectionViewModel.setIsBannerAvail(false);
        }
    }

    @Override
    public void onApiSuccess() {
        hideProgressBar();
        ((HomeFragment) getParentFragment()).setNotificationBadge();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgressBar();
        if (error.getHttpCode() == 0 && error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            mCollectionViewModel.setNoInternet(true);
            return;
        } else if (error.getHttpCode() == 19) {
            onNoData();
            mCollectionViewModel.setNoInternet(false);
            return;
        }
        mCollectionViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(mActivity, error.getHttpCode(), error.getMessage(), null);
    }
}
