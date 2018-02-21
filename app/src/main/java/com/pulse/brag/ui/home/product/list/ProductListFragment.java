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
import com.pulse.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.pulse.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.pulse.brag.views.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.pulse.brag.views.erecyclerview.loadmore.OnLoadMoreListener;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.home.product.quickadd.AddProductDialogFragment;
import com.pulse.brag.ui.home.product.details.ProductDetailFragment;
import com.pulse.brag.ui.home.product.list.adapter.ProductListAdapter;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.interfaces.OnAddButtonClickListener;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.pojo.response.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class ProductListFragment extends CoreFragment<FragmentProductListBinding, ProductListViewModel> implements ProductListNavigator,
        OnItemClickListener, OnAddButtonClickListener, OnProductSizeSelectListener,
        OnProductColorSelectListener /*BaseFragment implements BaseInterface,*/ {

    @Inject
    ProductListViewModel mProductListViewModel;
    @Inject
    GridLayoutManager mLayoutManager;

    FragmentProductListBinding mFragmentProductListBinding;


    /*View mView;
    ERecyclerView mRecyclerView;
    RecyclerView mRecyclerViewColor, mRecyclerViewSize;
    TextView mTxtQty;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mLinearSearch;
    RelativeLayout mRelativeSearch;
    ImageView mImgSort;*/

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

    int mSelectedQty;
    int productSize;

    List<ProductListResponse> collectionListRespones;
    ProductListAdapter mProductListAdapter;
    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    List<DummeyDataRespone> mDummeyDataRespones;
    boolean isViewWithCatalog = true;

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_product_list, container, false);
            initializeData();
            setListeners();
            ACTION = LOAD_LIST;
            checkNetworkConnection(true);
            showData();
        }
        return mView;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductListViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        collectionListRespones = new ArrayList<>();
        mDummeyDataRespones = new ArrayList<>();
        mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mDummeyDataRespones);
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductListBinding = getViewDataBinding();

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

                        if (mDummeyDataRespones.size() != productSize) {
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

        /*mFragmentProductListBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink));
        mFragmentProductListBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

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
        });*/

        ACTION = LOAD_LIST;

        checkNetworkConnection(true);
        /*mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mDummeyDataRespones);*/


    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_productlist));
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

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_productlist));
    }

    @Override
    public void initializeData() {

        mRecyclerView = (ERecyclerView) mView.findViewById(R.id.recycleView);
        mRelativeSearch = (RelativeLayout) mView.findViewById(R.id.relative_s);
        mImgSort = mView.findViewById(R.id.imageview_sort);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink));

        collectionListRespones = new ArrayList<>();
        mDummeyDataRespones = new ArrayList<>();

        mLinearSearch = (LinearLayout) mView.findViewById(R.id.linear_search);

        Utility.hideSoftkeyboard(getActivity());

    }

    @Override
    public void setListeners() {
//        mRecyclerView.loadMoreComplete(true);

//        mRecyclerView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
//        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (mSwipeRefreshLayout.isRefreshing()) {
//                            return;
//                        }
//
//                        if (mDummeyDataRespones.size() != productSize) {
//                            ACTION = LOAD_MORE;
//                            checkNetworkConnection(false);
//                        } else {
//                            mRecyclerView.loadMoreComplete(true);
//                        }
//                    }
//                }, 500);
//            }
//        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ACTION = LOAD_LIST;
                        PAGE_NUM = 1;
                        mRecyclerView.setIsLoadingMore(true);
                        checkNetworkConnection(false);
                    }
                }, 1000);

            }
        });

        mImgSort.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

//                swithcher();
            }
        });
    }

    @Override
    public void showData() {


        mProductListAdapter = new ProductListAdapter(getActivity(), this, this, mDummeyDataRespones);
        mRecyclerView.setPageSize(3);
        mRecyclerView.setAdapter(mProductListAdapter);

//        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setNestedScrollingEnabled(false);

    }*/


    private void checkNetworkConnection(boolean isShowLoader) {

        if (Utility.isConnection(getActivity())) {

            if (ACTION == LOAD_MORE) {
                PAGE_NUM++;
            }
            //GetProductListAPICall(isShowLoader);
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

    /*private void GetProductListAPICall(boolean isshowloader) {

        if (isshowloader)
            showProgressDialog();

        ApiClient.changeApiBaseUrl("https://reqres.in/api/");
        Call<DummeyRespone> mDataResponeCall = ApiClient.getInstance(getActivity()).getApiResp().getProductionList(PAGE_NUM);
        mDataResponeCall.enqueue(new Callback<DummeyRespone>() {
            @Override
            public void onResponse(Call<DummeyRespone> call, Response<DummeyRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    DummeyRespone respone = response.body();
                    switch (ACTION) {
                        case LOAD_LIST:

                            productSize = 0;
                            productSize = respone.getTotal();
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


//                            mDummeyDataRespones.addAll(respone.getData());
                            showData();
                            mSwipeRefreshLayout.setRefreshing(false);
                            break;
                        case LOAD_MORE:

                            mDummeyDataRespones.addAll(respone.getData());
                            mProductListAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete(false);
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<DummeyRespone> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getContext(), t);
                AlertUtils.showAlertMessage(getContext(), t);
            }
        });
    }*/


    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(ProductDetailFragment.newInstance(mDummeyDataRespones.get(position)), true, true);
    }

    @Override
    public void OnAddListener(final int position) {
        openDialogFragment(position);
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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QTY_REQUEST && resultCode == Activity.RESULT_OK) {
            mSelectedQty = data.getIntExtra(Constants.BUNDLE_QTY, 1);
            mTxtQty.setText("" + data.getIntExtra(Constants.BUNDLE_QTY, 1));
        }
    }*/


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
            //setToolbar();
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
    }

    @Override
    public void sort() {
        //swithcher();
    }

    @Override
    public void filter() {

    }

    @Override
    public void showList(int size, List<DummeyDataRespone> dataList) {
        switch (ACTION) {
            case LOAD_LIST:

                productSize = 0;
                productSize = size;
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
}
