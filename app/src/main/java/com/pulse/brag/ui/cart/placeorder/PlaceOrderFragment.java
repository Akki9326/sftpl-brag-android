package com.pulse.brag.ui.cart.placeorder;


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
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.ui.cart.adapter.PlaceOrderCartListAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentPlaceOrderBinding;
import com.pulse.brag.data.model.datas.CartListResponeData;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class PlaceOrderFragment extends CoreFragment<FragmentPlaceOrderBinding, PlaceOrderViewModel>
        implements PlaceOrderNavigator, PlaceOrderCartListAdapter.OnItemClick {


    public static final int REQUEST_QTY = 1;
    int positionQty;

    @Inject
    PlaceOrderViewModel mPlaceOrderViewModel;
    FragmentPlaceOrderBinding mFragmentPlaceOrderBinding;

    PlaceOrderCartListAdapter mAdapter;
    List<CartListResponeData> mList;

    public static PlaceOrderFragment newInstance(List<CartListResponeData> cartList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.BUNDLE_CART_LIST, (ArrayList<? extends Parcelable>) cartList);
        PlaceOrderFragment fragment = new PlaceOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlaceOrderViewModel.setNavigator(this);
    }

    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   /* mPlaceOrderViewModel.getCartData();*/
                }
            }, 500);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

    }

    @Override
    public void onContinueClick() {
        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditAddress() {
        Toast.makeText(getActivity(), "Edit or Add Address", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPriceLabelClick() {
        mFragmentPlaceOrderBinding.nestedScroll.smoothScrollTo(0, (mFragmentPlaceOrderBinding.viewDummy).getTop());

    }


    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentPlaceOrderBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentPlaceOrderBinding.baseLayout);
        initializeData();
        showData();
//        checkInternet();
    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, false, getResources().getString(R.string.toolbar_label_place_order));
    }

    @Override
    public PlaceOrderViewModel getViewModel() {
        return mPlaceOrderViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_place_order;
    }

    public void initializeData() {


        mFragmentPlaceOrderBinding.recycleview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFragmentPlaceOrderBinding.recycleview.setLayoutManager(layoutManager);
        mFragmentPlaceOrderBinding.recycleview.setMotionEventSplittingEnabled(false);
        mFragmentPlaceOrderBinding.recycleview.setNestedScrollingEnabled(false);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setRemoveDuration(200);
        mFragmentPlaceOrderBinding.recycleview.setItemAnimator(defaultItemAnimator);

        mList = new ArrayList<>();
        mList = getArguments().getParcelableArrayList(Constants.BUNDLE_CART_LIST);


    }

    public void showData() {

        mAdapter = new PlaceOrderCartListAdapter(getActivity(),mList, this);
        mFragmentPlaceOrderBinding.recycleview.setAdapter(mAdapter);
        setTotalPrice();


    }

    @Override
    public void onQtyClick(int position, CartListResponeData responeData) {

        // TODO: 05-12-2017 limit qty (max)
       /* positionQty = mList.indexOf(responeData);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_PRODUCT_NAME, mList.get(positionQty).getProduct_name());
        bundle.putInt(Constants.BUNDLE_QTY, mList.get(positionQty).getQty());
        bundle.putString(Constants.BUNDLE_PRODUCT_IMG, mList.get(positionQty).getProduct_image());

        EditQtyDialogFragment dialogFragment = new EditQtyDialogFragment();
        dialogFragment.setTargetFragment(this, REQUEST_QTY);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "");*/
    }

    private void setTotalPrice() {

        int total = 0;
        for (int i = 0; i < mList.size(); i++) {
            total += (mList.get(i).getQty()) * (mList.get(i).getPrice());
        }
        mPlaceOrderViewModel.setTotal(Utility.getIndianCurrencePriceFormate(total));
        mPlaceOrderViewModel.setListSize(mList.size());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_QTY && resultCode == 2 && data != null) {
            int qty = data.getIntExtra(Constants.BUNDLE_QTY, 0);
            //if old and new qty not same
            if (qty != mList.get(positionQty).getQty()) {
                showProgress();
                mAdapter.qtyUpdate(positionQty, qty);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                        mAdapter.notifyItemChanged(positionQty);
                        setTotalPrice();
                    }
                }, 1000);
            }


        }
    }
}
