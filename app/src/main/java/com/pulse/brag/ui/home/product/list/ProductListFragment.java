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
import android.support.v7.widget.LinearLayoutManager;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentProductListBinding;
import com.pulse.brag.callback.IOnProductButtonClickListener;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.data.model.DummeyDataRespone;
import com.pulse.brag.data.model.requests.AddToCartRequest;
import com.pulse.brag.data.model.response.ProductListResponse;
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

    List<ProductListResponse> collectionListRespones;
    ProductListAdapter mProductListAdapter;
    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    List<DummeyDataRespone> mDummeyDataRespones;
    boolean isViewWithCatalog = true;

    int mSelectedQty;
    int productListSize;

    String mTitle;
    int mSorting;
    boolean mFilterApplied;
    String mFilterColor;
    int mFilterColorPos;
    String mFilterSize;
    int mFilterSizePos;
    boolean isClicked = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductListViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_KEY_PRODUCT_LIST_TITLE))
            mTitle = getArguments().getString(BUNDLE_KEY_PRODUCT_LIST_TITLE);
        else
            mTitle = getString(R.string.toolbar_label_productlist);

        collectionListRespones = new ArrayList<>();
        mDummeyDataRespones = new ArrayList<>();
        mSorting = Constants.ProductSorting.POPULARITY.ordinal();
        mFilterApplied = false;
        mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mDummeyDataRespones);
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductListBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentProductListBinding.baseLayout);

        Utility.hideSoftkeyboard(getActivity());
        mFragmentProductListBinding.recycleView.setLayoutManager(mLayoutManager);
        mFragmentProductListBinding.recycleView.setHasFixedSize(true);
        mFragmentProductListBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentProductListBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        mFragmentProductListBinding.recycleView.loadMoreComplete(true);
        mFragmentProductListBinding.recycleView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentProductListBinding.recycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (mFragmentProductListBinding.swipeRefreshLayout.isRefreshing()) {
                            return;
                        }

                        if (mDummeyDataRespones.size() != productListSize) {
                            ACTION = LOAD_MORE;
                            checkNetworkConnection(false);
                        } else {
                            mFragmentProductListBinding.recycleView.loadMoreComplete(true);
                        }
                    }
                }, 500);
            }
        });
        mFragmentProductListBinding.recycleView.setPageSize(3);
        mFragmentProductListBinding.recycleView.setNestedScrollingEnabled(false);
        mFragmentProductListBinding.recycleView.setAdapter(mProductListAdapter);

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

            if (ACTION == LOAD_MORE) {
                PAGE_NUM++;
            }
            if (isShowLoader)
                showProgress();
            mProductListViewModel.getProductList(getBaseActivity(), PAGE_NUM);
        } else {
            mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
            //mProductListViewModel.setSwipeLoading(false);
            mFragmentProductListBinding.recycleView.loadMoreComplete(false);
            //Utility.showAlertMessage(getActivity(), 0, null);
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }


    @Override
    public void onItemClick(int position) {
        if (!isClicked) {
            isClicked = true;
            ((MainActivity) getActivity()).pushFragments(ProductDetailFragment.newInstance(mDummeyDataRespones.get(position)), true, true);
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
            ((MainActivity) getBaseActivity()).addToCartAPI(new AddToCartRequest());
            enabledClick();
        }
    }

    private void openDialogFragment(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, mDummeyDataRespones.get(position));
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
    public void OnSelectedSize(int pos) {
        mSizeListAdapter.setSelectedItem(pos);

    }


    private void swithcher() {
        int position;
        if (mProductListAdapter.getItemViewType(0) == 0) {
            position = ((LinearLayoutManager) mFragmentProductListBinding.recycleView.getLayoutManager()).findFirstVisibleItemPosition();
        } else {
            position = ((GridLayoutManager) mFragmentProductListBinding.recycleView.getLayoutManager()).findFirstVisibleItemPosition();
        }
        boolean isSwitched = mProductListAdapter.toggleItemViewType();
        mFragmentProductListBinding.recycleView.setLayoutManager(isSwitched ? new LinearLayoutManager(getActivity()) : new GridLayoutManager(getActivity(), 2));
        mProductListAdapter.notifyDataSetChanged();
        mFragmentProductListBinding.recycleView.scrollToPosition(position);

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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
        if (mFragmentProductListBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void sort() {
        //swithcher();
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
    public void filter() {
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
    public void showList(int size, List<DummeyDataRespone> dataList) {
        switch (ACTION) {
            case LOAD_LIST:

                productListSize = 0;
                productListSize = size;
                mDummeyDataRespones.clear();

                mDummeyDataRespones.add(new DummeyDataRespone(0,
                        "Classic Pullover T-shirt Bralette - Black Solid body with Black trims",
                        "",
                        "http://cdn.shopify.com/s/files/1/1629/9535/products/Chandini_SLIDE_Pattern_B_BRAG-Optical59961_large.jpg?v=1516609967"));

                mDummeyDataRespones.add(new DummeyDataRespone(0,
                        "Classic Pullover T-shirt Bralette - White with Black Print & Black trims",
                        "",
                        "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG-Optical60283_large.jpg?v=1516609966"));

                mDummeyDataRespones.add(new DummeyDataRespone(0,
                        "Classic Pullover T-shirt Bralette - White with Black Print & Black trims",
                        "",
                        "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG-Optical59472_large.jpg?v=1516609967"));


                mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mDummeyDataRespones);
                mFragmentProductListBinding.recycleView.setPageSize(3);
                mFragmentProductListBinding.recycleView.setAdapter(mProductListAdapter);
                mFragmentProductListBinding.recycleView.setNestedScrollingEnabled(false);
                mFragmentProductListBinding.swipeRefreshLayout.setRefreshing(false);
                //mProductListViewModel.setSwipeLoading(false);
                break;
            case LOAD_MORE:

                mDummeyDataRespones.addAll(dataList);
                mProductListAdapter.notifyDataSetChanged();
                mFragmentProductListBinding.recycleView.loadMoreComplete(false);

                /*to solved issue of move to bottom when load more*/
                //mFragmentProductListBinding.recycleView.scrollToPosition(mProductListAdapter.getItemCount()-1);

                break;
        }
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
    public void onSort(int type) {
        mSorting = type;
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
