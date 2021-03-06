package com.ragtagger.brag.ui.home.product.list;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */


/*
 * ERecycleView
 *
 *  mRecyclerView.loadMoreComplete(true)- hide footer loader,complate load more
 * */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.callback.IOnProductButtonClickListener;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataFilter;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.data.model.requests.QProductList;
import com.ragtagger.brag.databinding.FragmentProductListBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.HomeFragment;
import com.ragtagger.brag.ui.home.product.details.ProductDetailFragment;
import com.ragtagger.brag.ui.home.product.list.adapter.ProductListAdapter;
import com.ragtagger.brag.ui.home.product.list.filter.ProductFilterDialogFragment;
import com.ragtagger.brag.ui.home.product.list.sorting.ProductSortingDialogFragment;
import com.ragtagger.brag.ui.home.product.quickadd.AddProductDialogFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.ragtagger.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.ragtagger.brag.views.erecyclerview.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import static com.ragtagger.brag.utils.Constants.BUNDLE_KEY_PRODUCT_LIST_TITLE;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class ProductListFragment extends CoreFragment<FragmentProductListBinding, ProductListViewModel> implements ProductListNavigator,
        IOnItemClickListener, IOnProductButtonClickListener, ProductSortingDialogFragment.IOnSortListener, ProductFilterDialogFragment.IFilterApplyListener {

    //constants
    //view member
    //class member
    //basic member
    private static final int LOAD_LIST = 1;
    private static final int LOAD_MORE = 5;
    private int ACTION = 0;
    private int PAGE_NUM = 1;

    @Inject
    ProductListViewModel mProductListViewModel;
    @Inject
    GridLayoutManager mLayoutManager;
    FragmentProductListBinding mFragmentProductListBinding;

    HashMap<String, String> mColorFilter, mSizeFilter;
    List<DataProductList.Products> mProductList;

    ProductListAdapter mProductListAdapter;
    DataProductList mData;
    QProductList.Filter mRequestFilter;
    DataFilter.Filter mResponseFilter;

    int mSorting;
    int productListSize;
    boolean isClicked = false;
    boolean isFromQuickOrder = false;

    String mTitle;
    String mCategoryName;
    String mSubCategoryName;
    String mSeasonCode;
    String mSizeGuide;
    String mQuery;


    private OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (mFragmentProductListBinding.swipeRefreshLayout.isRefreshing()) {
                        return;
                    }

                    if (mProductList.size() != productListSize) {
                        ACTION = LOAD_MORE;
                        checkInternetAndCallApi(false);
                    } else {
                        mFragmentProductListBinding.recycleView.loadMoreComplete(true);
                    }
                }
            }, 500);
        }
    };

    private BroadcastReceiver mUpdateCartIcon = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Constants.ACTION_UPDATE_CART_ICON_STATE)) {
                if (intent.hasExtra(Constants.BUNDLE_POSITION)) {
                    int selectedPos = intent.getIntExtra(Constants.BUNDLE_POSITION, -1);
                    updateProductCartIcon(selectedPos, true);
                }
            }
        }
    };

    private BroadcastReceiver mUpdateCartIconList = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Constants.ACTION_UPDATE_LIST_CART_ICON_STATE)) {
                int pos = 0;
                for (DataProductList.Products product : mProductListAdapter.getList()) {
                    if (product.isAlreadyInCart())
                        updateProductCartIcon(pos, false);
                    pos++;
                }
            }
        }
    };

    public static ProductListFragment newInstance(String categoryId, String subCategoryId, String sizeGuide) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_NAME, categoryId);
        args.putString(Constants.BUNDLE_SUB_CATEGORY_NAME, subCategoryId);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstance(String seasonCode, String sizeGuide) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_SEASON_CODE, seasonCode);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstance(boolean isquickOrder) {
        Bundle args = new Bundle();
        args.putBoolean(Constants.BUNDLE_IS_QUICK_ORDER, isquickOrder);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_KEY_PRODUCT_LIST_TITLE, title);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductListViewModel.setNavigator(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mUpdateCartIcon, new IntentFilter(Constants.ACTION_UPDATE_CART_ICON_STATE));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mUpdateCartIconList, new IntentFilter(Constants.ACTION_UPDATE_LIST_CART_ICON_STATE));
    }

    @Override
    public void beforeViewCreated() {
        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_CATEGORY_NAME))
            mCategoryName = getArguments().getString(Constants.BUNDLE_CATEGORY_NAME);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SUB_CATEGORY_NAME))
            mSubCategoryName = getArguments().getString(Constants.BUNDLE_SUB_CATEGORY_NAME);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SEASON_CODE))
            mSeasonCode = getArguments().getString(Constants.BUNDLE_SEASON_CODE);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SIZE_GUIDE))
            mSizeGuide = getArguments().getString(Constants.BUNDLE_SIZE_GUIDE);


        if (getArguments() != null && getArguments().containsKey(BUNDLE_KEY_PRODUCT_LIST_TITLE))
            mTitle = getArguments().getString(BUNDLE_KEY_PRODUCT_LIST_TITLE);
        else
            mTitle = mSubCategoryName == null ? getString(R.string.toolbar_label_productlist) : mSubCategoryName;


        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_IS_QUICK_ORDER)) {
            isFromQuickOrder = true;
        }
        mSorting = Constants.SortBy.PRICE_ASC.ordinal();
        mRequestFilter = null;

        mProductList = new ArrayList<>();
        mColorFilter = new HashMap<>();
        mSizeFilter = new HashMap<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductListBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentProductListBinding.baseLayout);
        Utility.hideSoftkeyboard(getActivity());

        mProductListViewModel.setNoResult(false);
        mProductListViewModel.setNoInternet(false);

        mFragmentProductListBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternetAndCallApi(true);
            }
        });


        mFragmentProductListBinding.recycleView.setLayoutManager(mLayoutManager);
        mFragmentProductListBinding.recycleView.setHasFixedSize(true);
        mFragmentProductListBinding.recycleView.setItemViewCacheSize(30);
        mFragmentProductListBinding.recycleView.setDrawingCacheEnabled(true);
        mFragmentProductListBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentProductListBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
        mFragmentProductListBinding.recycleView.loadMoreComplete(true);
        mFragmentProductListBinding.recycleView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentProductListBinding.recycleView.setOnLoadMoreListener(mOnLoadMoreListener);

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
        // this is because we used from two different place one as child fragment(Quick Order of home tab), and other from subcategory, so for subcategory we need to set toolbar
        if (mActivity != null && mActivity instanceof ToolbarActivity && !isFromQuickOrder)
            ((ToolbarActivity) mActivity).showToolbar(true, false, true, mTitle);
    }

    @Override
    public ProductListViewModel getViewModel() {
        return mProductListViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_list;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateCartIcon);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateCartIconList);
    }

    private void enabledClick() {
        if (isClicked)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isClicked = false;
                }
            }, 250);
    }


    private void checkInternetAndCallApi(boolean isShowLoader) {
        if (isAdded())
            if (Utility.isConnection(mActivity)) {
                mProductListViewModel.setNoInternet(false);
                if (ACTION == LOAD_MORE) {
                    PAGE_NUM++;
                }
                if (isShowLoader)
                    showProgress();
                mProductListViewModel.getProductList(PAGE_NUM, mCategoryName, mSubCategoryName, mSeasonCode, mQuery, mSorting, mRequestFilter);
            } else {
                switch (ACTION) {
                    case LOAD_LIST:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProductListViewModel.setNoInternet(true);
                            }
                        }, 200);

                        break;
                    case LOAD_MORE:
                        AlertUtils.showAlertMessage(mActivity, 0, null, null);
                        break;
                }
                mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
                mFragmentProductListBinding.recycleView.loadMoreComplete(false);
            }
    }

    public void updateProductCartIcon(int pos, boolean inCart) {
        if (pos >= 0 && pos < mProductListAdapter.getList().size()) {
            DataProductList.Products item = mProductListAdapter.getItem(pos);
            if (item != null) {
                item.setAlreadyInCart(inCart);
                mProductListAdapter.notifyItemChanged(pos);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        if (!isClicked) {
            isClicked = true;
            openFragmentDetails(position);
            enabledClick();
        }
    }

    @Override
    public void OnAddListener(final int position) {
        if (!isClicked) {
            isClicked = true;
            openAddProductDialog(position);
            enabledClick();
        }
    }

    @Override
    public void onSort(int type) {
        mSorting = type;
        ACTION = LOAD_LIST;
        PAGE_NUM = 1;
        mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
        checkInternetAndCallApi(true);
    }

    @Override
    public void applyFilter(QProductList.Filter filter) {
        mRequestFilter = filter;
        ACTION = LOAD_LIST;
        PAGE_NUM = 1;
        mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
        checkInternetAndCallApi(true);
    }

    @Override
    public void performSwipeRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ACTION = LOAD_LIST;
                PAGE_NUM = 1;
                mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(true);
                mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
                checkInternetAndCallApi(false);
            }
        }, 1000);
    }


    @Override
    public void performSearch(String query) {
        hideKeyboard();
        mProductListViewModel.setNoResult(false);
        if (query != null && query.length() > 0) {
            mQuery = query;
            ACTION = LOAD_LIST;
            PAGE_NUM = 1;
            mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
            checkInternetAndCallApi(false);
        } else {
            mQuery = null;
            ACTION = LOAD_LIST;
            PAGE_NUM = 1;
            mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
            checkInternetAndCallApi(false);
        }
    }

    @Override
    public void performClickSort(View view) {
        openSortingDialog();
    }

    @Override
    public void openSortingDialog() {
        if (!isClicked) {
            isClicked = true;
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_PRODUCT_SORTING, mSorting);
            ProductSortingDialogFragment productSortingDialogFragment = new ProductSortingDialogFragment();
            productSortingDialogFragment.setCancelable(true);
            productSortingDialogFragment.setArguments(bundle);
            productSortingDialogFragment.show(getChildFragmentManager(), "");
            enabledClick();
        }
    }


    @Override
    public void performClickFilter(View view) {
        openFilterDialog();
    }

    @Override
    public void openFilterDialog() {
        if (!isClicked) {
            isClicked = true;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_CATEGORY_NAME, mCategoryName);
            bundle.putString(Constants.BUNDLE_SUB_CATEGORY_NAME, mSubCategoryName);
            bundle.putString(Constants.BUNDLE_SEASON_CODE, mSeasonCode);
            bundle.putParcelable(Constants.BUNDLE_APPLIED_FILTER, mResponseFilter);
            ProductFilterDialogFragment productFilterDialogFragment = new ProductFilterDialogFragment();
            productFilterDialogFragment.setCancelable(true);
            productFilterDialogFragment.setArguments(bundle);
            productFilterDialogFragment.show(getChildFragmentManager(), "");
            enabledClick();
        }
    }


    @Override
    public void setProductList(int size, List<DataProductList.Products> dataList) {
        mProductListViewModel.setNoResult(false);
        switch (ACTION) {
            case LOAD_LIST:
                productListSize = size;
                mProductList.clear();
                mProductList = dataList;

                mProductListAdapter = new ProductListAdapter(mActivity, this, this, mProductList);
                mFragmentProductListBinding.recycleView.setPageSize(20);
                mFragmentProductListBinding.recycleView.setAdapter(mProductListAdapter);
                mFragmentProductListBinding.recycleView.setNestedScrollingEnabled(false);
                mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
                if (size <= 20)
                    mFragmentProductListBinding.recycleView.setIsLoadingMore(false);
                break;
            case LOAD_MORE:
                mProductListAdapter.addList(dataList, mProductListAdapter.getItemCount(), dataList.size());
                mFragmentProductListBinding.recycleView.loadMoreComplete(false);
                break;
        }
    }

    @Override
    public void setFilter(DataFilter.Filter filter) {
        this.mResponseFilter = filter;
    }

    @Override
    public void onNoData() {
        switch (ACTION) {
            case LOAD_LIST:
                mProductListViewModel.setNoResult(true);
                break;
            case LOAD_MORE:
                mProductListViewModel.setNoResult(false);
                break;
        }
    }

    @Override
    public void setData(DataProductList data) {
        this.mData = data;
        this.mSeasonCode = data.getSeasonCode();
    }

    @Override
    public void openFragmentDetails(int position) {
        ((MainActivity) mActivity).pushFragments(ProductDetailFragment.newInstance(mData, mProductListAdapter.getList(), position, mSizeGuide, false), true, true);
    }

    @Override
    public void openAddProductDialog(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, mData);
        bundle.putInt(Constants.BUNDLE_POSITION, position);
        bundle.putString(Constants.BUNDLE_SIZE_GUIDE, mSizeGuide);
        bundle.putParcelableArrayList(Constants.BUNDLE_PRODUCT_LISt, (ArrayList<? extends Parcelable>) mProductListAdapter.getList());
        AddProductDialogFragment mAddProductDialogFragment = new AddProductDialogFragment();
        mAddProductDialogFragment.setCancelable(true);
        mAddProductDialogFragment.setArguments(bundle);
        mAddProductDialogFragment.show(getFragmentManager(), "");
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        if (getParentFragment() instanceof HomeFragment) {
            ((HomeFragment) getParentFragment()).setNotificationBadge();
        }
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();

        if (mFragmentProductListBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
        }
        if (error.getHttpCode() == 0 || error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            switch (ACTION) {
                case LOAD_LIST:
                    mProductListViewModel.setNoInternet(true);
                    break;
                case LOAD_MORE:
                    AlertUtils.showAlertMessage(mActivity, 0, null, null);
                    break;
            }
            return;
        }

        if (error.getHttpCode() == 19 && (mQuery != null && mQuery.length() > 0)) {
            switch (ACTION) {
                case LOAD_LIST:
                    mProductListViewModel.setNoResult(true);
                    break;
                case LOAD_MORE:
                    mProductListViewModel.setNoResult(false);
                    AlertUtils.showAlertMessage(mActivity, error.getHttpCode(), error.getMessage(), null);
                    break;
            }
            return;
        } else if (error.getHttpCode() == 19 && (mQuery == null || mQuery.length() == 0)) {
            if (ACTION == LOAD_LIST)
                mProductListViewModel.setNoResult(true);
            mProductListViewModel.setNoInternet(false);
            return;
        }

        mProductListViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(mActivity, error.getHttpCode(), error.getMessage(), null);
    }
}
