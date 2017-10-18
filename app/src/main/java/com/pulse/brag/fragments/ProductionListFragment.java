package com.pulse.brag.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.ProductListAdapter;
import com.pulse.brag.erecyclerview.ERecyclerView;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.pulse.brag.erecyclerview.loadmore.OnLoadMoreListener;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.CollectionListRespone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 27-09-2017.
 */


public class ProductionListFragment extends BaseFragment implements BaseInterface, OnItemClickListener {

    View mView;
    ERecyclerView mRecyclerView;

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

    List<CollectionListRespone> collectionListRespones;
    ProductListAdapter mProductListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_product_list, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
        ((BaseActivity) getActivity()).showToolbar(true, false, true, "Product List");
    }

    @Override
    public void initializeData() {

        mRecyclerView = (ERecyclerView) mView.findViewById(R.id.recycleView);
        mRecyclerView.setPageSize(PAGE_SIZE);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));

        collectionListRespones = new ArrayList<>();


    }

    @Override
    public void setListeners() {

        mRecyclerView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (!isExecuteAsync) {
                            if (ACTION == LOAD_LIST || ACTION == LOAD_MORE) {

                                if (collectionListRespones.size() != productCount) {
//                                    pageNumber++;
                                    ACTION = LOAD_MORE;
                                    checkNetworkConnection();
                                } else {
                                    mRecyclerView.loadMoreComplete(true);
                                }
                            }

                            if (ACTION == SEARCH || ACTION == SEARCH_LOAD_MORE) {
                                if (collectionListRespones.size() != productCount) {
//                                    pageNumber++;
                                    ACTION = SEARCH_LOAD_MORE;
                                    checkNetworkConnection();
                                } else {
                                    mRecyclerView.loadMoreComplete(true);
                                }
                            }
                        }
                    }
                }, 500);
            }
        });
    }

    private void checkNetworkConnection() {

        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/7_front_large.jpg?v=1491587747", "Collection", "", "1562"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/2_front_large.jpg?v=1491583335", "More", "", "496231"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/5_front_large.jpg?v=1491583722", "", "", "12512"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/blue-print-classic-pullover-product-page_front_large.jpg?v=1482923591", "", "", "162311"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/pink-print-classic-pullover-prodouct-page_back_large.jpg?v=1482923582", "", "", "5445"));

        isExecuteAsync = true;


        isExecuteAsync = false;

        mProductListAdapter.notifyDataSetChanged();
        //stop the pull to refresh loader
        mRecyclerView.loadMoreComplete(false);


    }

    @Override
    public void showData() {

        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/7_front_large.jpg?v=1491587747", "Collection", "", "699.00"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/2_front_large.jpg?v=1491583335", "More", "", "966.30"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/5_front_large.jpg?v=1491583722", "", "", "1200.52"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/blue-print-classic-pullover-product-page_front_large.jpg?v=1482923591", "", "", "1200"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/pink-print-classic-pullover-prodouct-page_back_large.jpg?v=1482923582", "", "", "15600"));
        collectionListRespones.add(new CollectionListRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/pink-print-classic-pullover-collection-page_front_large.jpg?v=1480551969", "", "", "1596"));

        mProductListAdapter = new ProductListAdapter(getActivity(), this, collectionListRespones);
        mRecyclerView.setAdapter(mProductListAdapter);

        mRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onItemClick(int position) {

        ((BaseActivity) getActivity()).pushFragments(new ProductDetailFragment(), true, true);
    }
}
