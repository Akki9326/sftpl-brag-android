package com.pulse.brag.fragments;


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
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.CartListAdapter;
import com.pulse.brag.adapters.PriceListAdapter;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.interfaces.OnQtyClickListener;
import com.pulse.brag.pojo.datas.CartListResponeData;
import com.pulse.brag.pojo.datas.PriceListData;
import com.pulse.brag.views.OnSingleClickListener;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nikhil.vadoliya on 07-11-2017.
 */


public class CartFragment extends BaseFragment implements BaseInterface, OnItemClickListener, OnQtyClickListener {

    public static final int REQUEST_QTY = 1;

    View mView;
    TextView mTxtTotalPrice, mTxtViewDetail;
    RecyclerView mRecyclerView;
    ListView mListView;
    NestedScrollView mNestedScrollView;

    CartListAdapter mAdapter;

    int positionQty;

    List<CartListResponeData> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cart, container, false);
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
        ((BaseActivity) getActivity()).showToolbar(true, false, false, getResources().getString(R.string.toolbar_label_cart));
    }

    @Override
    public void initializeData() {

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        ;

        mTxtTotalPrice = (TextView) mView.findViewById(R.id.textview_total_price);
        mTxtViewDetail = (TextView) mView.findViewById(R.id.textview_view_detail);
        mNestedScrollView = (NestedScrollView) mView.findViewById(R.id.nested_scroll);

//        mListView = (ListView) mView.findViewById(R.id.listview);
        Utility.applyTypeFace(getActivity(), (RelativeLayout) mView.findViewById(R.id.base_layout));

    }

    @Override
    public void setListeners() {

        mTxtViewDetail.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mNestedScrollView.smoothScrollTo(0, ((View) mView.findViewById(R.id.dummy_view)).getTop());
            }
        });
    }

    @Override
    public void showData() {

        mList = new ArrayList<>();
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Product Name", "L", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#F44336", 100, 2));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0135_2_large.jpg?v=1495697256",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#9C27B0", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/yellow-caged_front_large.jpg?v=1480569528",
                "Plunge Neck Cage Back T-shirt Bralette - Yellow", "L", "#2196F3", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/pink-printed-cage_front_large.jpg?v=1482476302",
                "Plunge Neck Cage Back T-shirt Bralette - Pink", "L", "#FF5722", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Product Name", "L", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#F44336", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Product Name", "L", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#F44336", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Product Name", "L", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#F44336", 500, 1));

        mAdapter = new CartListAdapter(getActivity(), mList, this, this);
        mRecyclerView.setAdapter(mAdapter);

        setTotalPrice();

    }

    private void setTotalPrice() {

        int total = 0;
        for (int i = 0; i < mList.size(); i++) {
            total += (mList.get(i).getQty()) * (mList.get(i).getPrice());
        }
        mTxtTotalPrice.setText(Utility.getIndianCurrencePriceFormate(total));
//        setPriceDetailListData();
    }

    private void setPriceDetailListData() {

        List<PriceListData> mListData = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            mListData.add(new PriceListData(mList.get(i).getProduct_name(),
                    (mList.get(i).getPrice()) * (mList.get(i).getQty()),
                    "" + mList.get(i).getQty() + " * " + mList.get(i).getPrice()));
        }

        mListView.setAdapter(new PriceListAdapter(getActivity(), mListData));
        Utility.setListViewHeightBasedOnChildren(mListView);
    }

    @Override
    public void onItemClick(int position) {
//        showProgressDialog();
        mList.remove(position);
        mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount() - 1);
        mAdapter.notifyItemRemoved(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                hideProgressDialog();
                mAdapter.notifyDataSetChanged();
            }
        }, 500);
        setTotalPrice();
    }

    @Override
    public void onQtyClick(int position) {
        // TODO: 05-12-2017 limit qty (max)
        positionQty = position;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_PRODUCT_NAME, mList.get(position).getProduct_name());
        bundle.putInt(Constants.BUNDLE_QTY, mList.get(position).getQty());
        bundle.putString(Constants.BUNDLE_PRODUCT_IMG,mList.get(position).getProduct_image());

        EditQtyDialogFragment dialogFragment = new EditQtyDialogFragment();
        dialogFragment.setTargetFragment(this, REQUEST_QTY);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_QTY && resultCode == 2 && data != null) {
            int qty = data.getIntExtra(Constants.BUNDLE_QTY, 0);
            //if old and new qty not same
            if (qty != mList.get(positionQty).getQty()) {
                showProgressDialog();
                mList.get(positionQty).setQty(qty);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        hideProgressDialog();
                        mAdapter.notifyItemChanged(positionQty);
                        mAdapter.notifyDataSetChanged();
                        setTotalPrice();
                    }
                }, 1000);
            }


        }
    }
}
