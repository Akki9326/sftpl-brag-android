package com.pulse.brag.ui.cart;


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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.ui.cart.adapter.CartListAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentCartBinding;
import com.pulse.brag.ui.cart.placeorder.PlaceOrderFragment;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.cart.editquantity.EditQtyDialogFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.data.model.datas.CartData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 07-11-2017.
 */


public class CartFragment extends CoreFragment<FragmentCartBinding, CartViewModel> implements CartNavigator, CartListAdapter.OnItemClick {

    public static final int REQUEST_QTY = 1;
    int positionQty;


    CartListAdapter mAdapter;
    List<CartData> mList;

    @Inject
    CartViewModel cartViewModel;

    FragmentCartBinding mFragmentCartBinding;
    CartData mCartItemDelete;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel.setNavigator(this);
    }


    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            if (!mFragmentCartBinding.swipeRefreshLayout.isRefreshing())
                showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cartViewModel.getCartData();
                }
            }, 500);
        } else {
            hideLoader();
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }


    @Override
    public void beforeViewCreated() {
    }

    @Override
    public void afterViewCreated() {
        mFragmentCartBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentCartBinding.baseLayout);
        initializeData();
        checkInternet();
    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, false, getResources().getString(R.string.toolbar_label_cart));
    }

    @Override
    public CartViewModel getViewModel() {
        return cartViewModel;
    }


    public void initializeData() {

        mFragmentCartBinding.recycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFragmentCartBinding.recycleView.setLayoutManager(layoutManager);
        mFragmentCartBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentCartBinding.recycleView.setNestedScrollingEnabled(false);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setRemoveDuration(200);
        mFragmentCartBinding.recycleView.setItemAnimator(defaultItemAnimator);

        //it's do for swipe to refersh issue
        mFragmentCartBinding.linearNoCart.setVisibility(View.GONE);
        mFragmentCartBinding.linearCart.setVisibility(View.VISIBLE);
        mFragmentCartBinding.linearBasePrice.setVisibility(View.GONE);
        mList = new ArrayList<>();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_QTY && resultCode == 2 && data != null) {
            int qty = data.getIntExtra(Constants.BUNDLE_QTY, 0);
            //if old and new qty not same
            if (qty != mList.get(positionQty).getQuantity()) {
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


    @Override
    public void onDeleteItem(final int position, final CartData responeData) {

        if (Utility.isConnection(getActivity())) {
            showProgress();
            mCartItemDelete = responeData;
            cartViewModel.removeFromCart(responeData.getItemId());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void onQtyClick(int position, CartData responeData) {
        // TODO: 05-12-2017 limit qty (max)
        positionQty = mList.indexOf(responeData);
        Bundle bundle = new Bundle();
//        bundle.putString(Constants.BUNDLE_PRODUCT_NAME, mList.get(positionQty).getProduct_name());
        bundle.putInt(Constants.BUNDLE_QTY, mList.get(positionQty).getQuantity());
//        bundle.putString(Constants.BUNDLE_PRODUCT_IMG, mList.get(positionQty).getProduct_image());
        bundle.putInt(Constants.BUNDLE_NUM_STOCK, mList.get(position).getItem().getStockData());
        EditQtyDialogFragment dialogFragment = new EditQtyDialogFragment();
        dialogFragment.setTargetFragment(this, REQUEST_QTY);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "");
    }


    @Override
    public void onApiSuccess() {
        hideLoader();
        mFragmentCartBinding.linearBasePrice.setVisibility(View.VISIBLE);
    }

    @Override
    public void onApiError(ApiError error) {
        hideLoader();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void onContinuesClick() {
        ((MainActivity) getActivity()).pushFragments(PlaceOrderFragment.newInstance(mList), true, true);

    }

    @Override
    public void onPriceClick() {
//        mFragmentCartBinding.recycleView.scrollToPosition(mList.size() - 1);
    }

    @Override
    public void swipeRefresh() {
        mFragmentCartBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternet();
    }

    @Override
    public void getCartList(List<CartData> list) {

        mList = new ArrayList<>();
        mList.addAll(list);
        mAdapter = new CartListAdapter(getActivity(), mList, this);
        mFragmentCartBinding.recycleView.setAdapter(mAdapter);
        setTotalPrice();
        cartViewModel.setListVisibility(mList.isEmpty() ? false : true);
    }

    @Override
    public void onSuccessDeleteFromAPI() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                mAdapter.removeItem(mCartItemDelete);
                setTotalPrice();
                if (mAdapter.getItemCount() == 0) {
                    cartViewModel.setListVisibility(false);
                }
            }
        }, 500);
    }

    @Override
    public void onErrorDeleteFromAPI(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    private void setTotalPrice() {

        int total = 0;
        for (int i = 0; i < mList.size(); i++) {
            total += (mList.get(i).getQuantity()) * (mList.get(i).getItem().getUnitPrice());
        }
        cartViewModel.setTotal(Utility.getIndianCurrencyPriceFormatWithComma(total));
        cartViewModel.setListNum(mList.size());
    }

    public void hideLoader() {
        if (mFragmentCartBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentCartBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
        }
    }
}
