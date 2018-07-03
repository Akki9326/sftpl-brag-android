package com.ragtagger.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.os.Handler;
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
import com.ragtagger.brag.data.model.datas.DataSubCategory;
import com.ragtagger.brag.data.model.response.RImagePager;
import com.ragtagger.brag.databinding.FragmentSubCategoryBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.adapter.CategoryListAdapter;
import com.ragtagger.brag.ui.home.adapter.SubCategoryListAdapter;
import com.ragtagger.brag.ui.home.product.list.ProductListFragment;
import com.ragtagger.brag.ui.home.product.list.adapter.ProductListAdapter;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.ragtagger.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.ragtagger.brag.views.erecyclerview.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 29-01-2018.
 */


public class SubCategoryFragment extends CoreFragment<FragmentSubCategoryBinding, SubCategoryViewModel> implements SubCategoryNavigator, IOnItemClickListener {

    private static final int LOAD_LIST = 1;
    private static final int LOAD_MORE = 5;
    private int ACTION = 0;
    private int PAGE_NUM = 1;

    @Inject
    SubCategoryViewModel categoryViewModel;
    FragmentSubCategoryBinding mFragmentSubCategoryBinding;

    List<DataSubCategory.DataObject> mSubCategoryList;
    List<RImagePager> imagePagerResponeList;
    String mCategory;
    String mSizeGuide;
    SubCategoryListAdapter adapter;
    DataCategoryList.Category mSelectedCategory;
    int subCategoryListSize;


    private OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mFragmentSubCategoryBinding.swipeRefreshLayout.isRefreshing()) {
                        mFragmentSubCategoryBinding.recycleView.loadMoreComplete(false);
                        return;
                    }
                    if (mSubCategoryList.size() != subCategoryListSize) {
                        ACTION = LOAD_MORE;
                        checkInternetAndCallApi(false);
                    } else {
                        mFragmentSubCategoryBinding.recycleView.loadMoreComplete(true);
                    }
                }
            }, 500);
        }
    };


    public static SubCategoryFragment newInstance(String category, String url, List<DataCategoryList.Category> list,
                                                  String sizeGuide, DataCategoryList.Category mSelectedCategory) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_NAME, category);
        args.putString(Constants.BUNDLE_IMAGE_URL, url);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
        args.putParcelable(Constants.BUNDLE_CATEGORY_OBJECT, mSelectedCategory);
        args.putParcelableArrayList(Constants.BUNDLE_CATEGORY_LIST, (ArrayList<? extends Parcelable>) list);
        SubCategoryFragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /*public static SubCategoryFragment newInstance(DataCategoryList.Category mSelectedCategory) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_CATEGORY_OBJECT, mSelectedCategory);
        SubCategoryFragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }*/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mSubCategoryList = Collections.emptyList();
        imagePagerResponeList = Collections.emptyList();
        /*if (getArguments().containsKey(Constants.BUNDLE_CATEGORY_LIST)) {
            mSubCategoryList = getArguments().getParcelableArrayList(Constants.BUNDLE_CATEGORY_LIST);
        }*/
        if (getArguments().containsKey(Constants.BUNDLE_IMAGE_URL)) {
            categoryViewModel.setProductImg(getArguments().getString(Constants.BUNDLE_IMAGE_URL));
        }
        if (getArguments().containsKey(Constants.BUNDLE_CATEGORY_NAME)) {
            mCategory = getArguments().getString(Constants.BUNDLE_CATEGORY_NAME);
        }
        if (getArguments().containsKey(Constants.BUNDLE_SIZE_GUIDE)) {
            mSizeGuide = getArguments().getString(Constants.BUNDLE_SIZE_GUIDE);
        }
        if (getArguments().containsKey(Constants.BUNDLE_CATEGORY_OBJECT)) {
            mSelectedCategory = getArguments().getParcelable(Constants.BUNDLE_CATEGORY_OBJECT);
            categoryViewModel.setSelectedCategory(mSelectedCategory);
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

        mFragmentSubCategoryBinding.recycleView.loadMoreComplete(true);
        mFragmentSubCategoryBinding.recycleView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentSubCategoryBinding.recycleView.setOnLoadMoreListener(mOnLoadMoreListener);

        mFragmentSubCategoryBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternetAndCallApi(true);
            }
        });
        ACTION = LOAD_LIST;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInternetAndCallApi(true);
            }
        }, 300);


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


    private void checkInternetAndCallApi(boolean isShowLoader) {
        if (isAdded())
            if (Utility.isConnection(mActivity)) {
                categoryViewModel.setNoInternet(false);
                if (ACTION == LOAD_MORE) {
                    PAGE_NUM++;
                }
                if (isShowLoader)
                    showProgress();
                categoryViewModel.callGetSubCategoryDataApi(PAGE_NUM, mSelectedCategory.getId());
            } else {
                hideProgressBar();
                switch (ACTION) {
                    case LOAD_LIST:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                categoryViewModel.setNoInternet(true);
                            }
                        }, 200);

                        break;
                    case LOAD_MORE:
                        AlertUtils.showAlertMessage(mActivity, 0, null, null);
                        break;
                }
                mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
                mFragmentSubCategoryBinding.recycleView.loadMoreComplete(false);
            }
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
        ((MainActivity) mActivity).pushFragments(ProductListFragment.newInstance(mCategory, mSubCategoryList.get(position).getOptionName(), mSizeGuide), true, true);
    }

    @Override
    public void performSwipeRefresh() {
        if (!mFragmentSubCategoryBinding.recycleView.isLoadingData()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ACTION = LOAD_LIST;
                    PAGE_NUM = 1;
                    mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
                    mFragmentSubCategoryBinding.recycleView.setIsLoadingMore(true);
                    checkInternetAndCallApi(false);
                }
            }, 1000);
        } else {
            mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
            mFragmentSubCategoryBinding.recycleView.setIsLoadingMore(true);
        }
    }

    @Override
    public void onNoData() {
        switch (ACTION) {
            case LOAD_LIST:
                categoryViewModel.setNoResult(true);
                break;
            case LOAD_MORE:
                categoryViewModel.setNoResult(false);
                break;
        }
    }

    @Override
    public void setCategoryList(int count, List<DataSubCategory.DataObject> list) {
        categoryViewModel.setNoResult(false);
        switch (ACTION) {
            case LOAD_LIST:
                subCategoryListSize = count;
                if (mSubCategoryList == null) {
                    mSubCategoryList = new ArrayList<>();
                }
                mSubCategoryList.clear();
                mSubCategoryList = list;
                adapter = new SubCategoryListAdapter(getContext(), mSubCategoryList, this);
                mFragmentSubCategoryBinding.recycleView.setPageSize(20);
                mFragmentSubCategoryBinding.recycleView.setAdapter(adapter);
                mFragmentSubCategoryBinding.recycleView.setNestedScrollingEnabled(false);
                mFragmentSubCategoryBinding.swipeRefreshLayout.setRefreshing(false);
                if (count <= 20)
                    mFragmentSubCategoryBinding.recycleView.setIsLoadingMore(false);
                break;
            case LOAD_MORE:
                adapter.addList(list, adapter.getItemCount(), list.size());
                mFragmentSubCategoryBinding.recycleView.loadMoreComplete(false);
                break;
        }
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
