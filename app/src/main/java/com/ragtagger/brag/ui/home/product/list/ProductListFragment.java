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
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.product.details.ProductDetailFragment;
import com.ragtagger.brag.ui.home.product.list.adapter.ProductListAdapter;
import com.ragtagger.brag.ui.home.product.list.filter.ProductFilterDialogFragment;
import com.ragtagger.brag.ui.home.product.list.sorting.ProductSortingDialogFragment;
import com.ragtagger.brag.ui.home.product.quickadd.AddProductDialogFragment;
import com.ragtagger.brag.ui.main.MainActivity;
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


    public static final int REQ_ADDED_TO_CART = 2221;
    public static final int RESULT_OK = 0;

    @Inject
    ProductListViewModel mProductListViewModel;
    @Inject
    GridLayoutManager mLayoutManager;

    FragmentProductListBinding mFragmentProductListBinding;

    private static final int LOAD_LIST = 1;
    private static final int LOAD_MORE = 5;

    private int ACTION = 0;
    private int PAGE_NUM = 1;
    int mSorting;
    int productListSize;
    boolean isClicked = false;

    String mTitle;
    String mCategoryName;
    String mSubCategoryName;
    String mSeasonCode;
    String mSizeGuide;
    String mQuery;

    ProductListAdapter mProductListAdapter;

    List<DataProductList.Products> mProductList;
    DataProductList mData;
    QProductList.Filter mRequestFilter;
    DataFilter.Filter mResponseFilter;

    HashMap<String, String> mColorFilter;
    HashMap<String, String> mSizeFilter;


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
                        checkNetworkConnection(false);
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
                    updateProductCartIcon(selectedPos);
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
                checkNetworkConnection(true);
            }
        });


        mFragmentProductListBinding.recycleView.setLayoutManager(mLayoutManager);
        mFragmentProductListBinding.recycleView.setHasFixedSize(true);
        mFragmentProductListBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentProductListBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        mFragmentProductListBinding.recycleView.loadMoreComplete(true);
        mFragmentProductListBinding.recycleView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentProductListBinding.recycleView.setOnLoadMoreListener(mOnLoadMoreListener);

        ACTION = LOAD_LIST;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkNetworkConnection(true);
            }
        }, 300);

    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, true, mTitle);
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
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateCartIcon);
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


    private void checkNetworkConnection(boolean isShowLoader) {

        if (Utility.isConnection(getActivity())) {
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
                    mProductListViewModel.setNoInternet(true);
                    break;
                case LOAD_MORE:
                    AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                    break;
            }
            mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
            mFragmentProductListBinding.recycleView.loadMoreComplete(false);
        }
    }

    public void updateProductCartIcon(int pos) {
        if (pos >= 0 && pos < mProductListAdapter.getList().size()) {
            DataProductList.Products item = mProductListAdapter.getItem(pos);
            if (item != null) {
                item.setAlreadyInCart(true);
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
    public void OnCartClick(int position) {
        if (!isClicked) {
            isClicked = true;
            // TODO: 15-03-2018 :oncart click
//            ((MainActivity) getBaseActivity()).updateCartNum(0);
//            enabledClick();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
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
                    AlertUtils.showAlertMessage(getActivity(), 0, null, null);
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
                    AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
                    break;
            }
            return;
        }

        mProductListViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);

    }

    @Override
    public void swipeRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ACTION = LOAD_LIST;
                PAGE_NUM = 1;
                mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(true);
                mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
                checkNetworkConnection(false);
            }
        }, 1000);
    }


    @Override
    public void search(String query) {
        hideKeyboard();
        if (query != null && query.length() > 0) {
            mQuery = query;
            ACTION = LOAD_LIST;
            PAGE_NUM = 1;
            mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
            checkNetworkConnection(false);
        } else {
            mQuery = null;
            ACTION = LOAD_LIST;
            PAGE_NUM = 1;
            mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
            checkNetworkConnection(false);
        }
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
    public void setProductList(int size, List<DataProductList.Products> dataList) {
        mProductListViewModel.setNoResult(false);
        switch (ACTION) {
            case LOAD_LIST:
                productListSize = size;
                mProductList.clear();
                mProductList = dataList;

                mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mProductList);
                mFragmentProductListBinding.recycleView.setPageSize(20);
                mFragmentProductListBinding.recycleView.setAdapter(mProductListAdapter);
                mFragmentProductListBinding.recycleView.setNestedScrollingEnabled(false);
                mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
                if (size <= 20)
                    mFragmentProductListBinding.recycleView.setIsLoadingMore(false);
                break;
            case LOAD_MORE:
                mProductListAdapter.addList(dataList);
                mFragmentProductListBinding.recycleView.loadMoreComplete(false);
                break;
        }
    }

    @Override
    public void setFilter(DataFilter.Filter filter) {
        this.mResponseFilter = filter;
    }

    @Override
    public void openFragmentDetails(int position) {
        ((MainActivity) getActivity()).pushFragments(ProductDetailFragment.newInstance(mData, mProductListAdapter.getList(), position, mSizeGuide/*, this, REQ_ADDED_TO_CART*/), true, true);
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
        /*mAddProductDialogFragment.setTargetFragment(this, REQ_ADDED_TO_CART);*/
        mAddProductDialogFragment.show(getFragmentManager(), "");
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
    public void onSort(int type) {
        mSorting = type;
        ACTION = LOAD_LIST;
        PAGE_NUM = 1;
        mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
        checkNetworkConnection(true);
    }

    @Override
    public void applyFilter(QProductList.Filter filter) {
        mRequestFilter = filter;
        ACTION = LOAD_LIST;
        PAGE_NUM = 1;
        mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
        checkNetworkConnection(true);
    }


}
