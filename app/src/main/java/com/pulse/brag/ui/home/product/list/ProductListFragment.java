package com.pulse.brag.ui.home.product.list;

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

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.callback.IOnProductButtonClickListener;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataProductList;
import com.pulse.brag.databinding.FragmentProductListBinding;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.home.product.details.ProductDetailFragment;
import com.pulse.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.pulse.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.pulse.brag.ui.home.product.list.adapter.ProductListAdapter;
import com.pulse.brag.ui.home.product.list.filter.ProductFilterDialogFragment;
import com.pulse.brag.ui.home.product.list.sorting.ProductSortingDialogFragment;
import com.pulse.brag.ui.home.product.quickadd.AddProductDialogFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.pulse.brag.views.erecyclerview.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import static com.pulse.brag.utils.Constants.BUNDLE_KEY_PRODUCT_LIST_TITLE;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class ProductListFragment extends CoreFragment<FragmentProductListBinding, ProductListViewModel> implements ProductListNavigator,
        IOnItemClickListener, IOnProductButtonClickListener, IOnProductSizeSelectListener,
        IOnProductColorSelectListener, ProductSortingDialogFragment.IOnSortListener, ProductFilterDialogFragment.IFilterApplyListener/*BaseFragment implements BaseInterface,*/ {


    @Inject
    ProductListViewModel mProductListViewModel;
    @Inject
    GridLayoutManager mLayoutManager;

    FragmentProductListBinding mFragmentProductListBinding;

    boolean isExecuteAsync = false;
    private static final int LOAD_LIST = 1;
    private static final int REFINE = 2;
    private static final int SORT = 3;
    private static final int SEARCH = 4;
    private static final int LOAD_MORE = 5;
    private static final int SEARCH_LOAD_MORE = 6;
    private static final int PAGE_SIZE = 5;
    private int ITEM_SPACING;
    private int ACTION = 0;
    int productCount = 60;
    private int PAGE_NUM = 1;
    private static final int QTY_REQUEST = 1;

    ProductListAdapter mProductListAdapter;
    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    List<DataProductList.Products> mProductList;
    boolean isViewWithCatalog = true;

    int mSelectedQty;
    int productListSize;

    String mTitle;
    int mSorting;
    DataProductList.Filter mFilter;

    boolean mFilterApplied;
    String mFilterColor;
    int mFilterColorPos;
    String mFilterSize;
    int mFilterSizePos;
    boolean isClicked = false;

    String mCategoryName;
    String mSubCategoryName;

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

    public static ProductListFragment newInstance(String categoryId, String subCategoryId) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_CATEGORY_NAME, categoryId);
        args.putString(Constants.BUNDLE_SUB_CATEGORY_NAME, subCategoryId);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductListViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_CATEGORY_NAME))
            mCategoryName = getArguments().getString(Constants.BUNDLE_CATEGORY_NAME);

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SUB_CATEGORY_NAME))
            mSubCategoryName = getArguments().getString(Constants.BUNDLE_SUB_CATEGORY_NAME);

        if (getArguments() != null && getArguments().containsKey(BUNDLE_KEY_PRODUCT_LIST_TITLE))
            mTitle = getArguments().getString(BUNDLE_KEY_PRODUCT_LIST_TITLE);
        else
            mTitle = mSubCategoryName == null ? getString(R.string.toolbar_label_productlist) : mSubCategoryName;


        mSorting = Constants.SortBy.PRICE_ASC.ordinal();
        mFilter = null;
        mFilterApplied = false;

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
        checkNetworkConnection(true);
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
            mProductListViewModel.getProductList(PAGE_NUM, mCategoryName, mSubCategoryName, mSorting, mFilter);
        } else {
            switch (ACTION) {
                case LOAD_LIST:
                    mProductListViewModel.setNoInternet(true);
                    break;
                case LOAD_MORE:
                    AlertUtils.showAlertMessage(getActivity(), 0, null);
                    break;
            }
            mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
            mFragmentProductListBinding.recycleView.loadMoreComplete(false);
        }
    }


    @Override
    public void onItemClick(int position) {
        if (!isClicked) {
            isClicked = true;
            ((MainActivity) getActivity()).pushFragments(ProductDetailFragment.newInstance(mProductList.get(position)), true, true);
            enabledClick();
        }
    }

    @Override
    public void OnAddListener(final int position) {
        if (!isClicked) {
            isClicked = true;
            openDialogFragment(position);
            enabledClick();
        }
    }

    @Override
    public void OnCartClick(int position) {
        if (!isClicked) {
            isClicked = true;
            ((MainActivity) getBaseActivity()).addToCartAPI(0);
            enabledClick();
        }
    }

    private void openDialogFragment(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, mProductList.get(position));
        AddProductDialogFragment mAddProductDialogFragment = new AddProductDialogFragment();
        mAddProductDialogFragment.setCancelable(true);
        mAddProductDialogFragment.setArguments(bundle);
        mAddProductDialogFragment.show(getChildFragmentManager(), "");
    }

    @Override
    public void onSeleteColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
    }


    @Override
    public void OnSelectedSize(int prevPos, int pos, DataProductList.Size item) {
        mSizeListAdapter.setSelectedItem(pos);
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
        if (error.getHttpCode() == 0 && error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            switch (ACTION) {
                case LOAD_LIST:
                    mProductListViewModel.setNoInternet(true);
                    break;
                case LOAD_MORE:
                    AlertUtils.showAlertMessage(getActivity(), 0, null);
                    break;
            }
            return;
        }
        mProductListViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());

    }

    @Override
    public void swipeRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ACTION = LOAD_LIST;
                PAGE_NUM = 1;
                mFragmentProductListBinding.recycleView.setIsLoadingMore(true);
                checkNetworkConnection(false);
            }
        }, 1000);
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
    public void setFilter(DataProductList.Filter filter) {
        this.mFilter = filter;
    }

    @Override
    public void openSortDialog() {
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
    public void openFilter() {
        if (!isClicked) {
            isClicked = true;
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.BUNDLE_PRODUCT_FILTER_APPLIED, mFilterApplied);
            bundle.putInt(Constants.BUNDLE_PRODUCT_FILTER_COLOR, mFilterColorPos);
            bundle.putInt(Constants.BUNDLE_PRODUCT_FILTER_SIZE, mFilterSizePos);
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
    public void applyFilter(String color, int colorPos, String size, int sizePos) {
        mFilterApplied = true;
        mFilterColor = color;
        mFilterColorPos = colorPos;
        mFilterSize = size;
        mFilterSizePos = sizePos;
    }


}
