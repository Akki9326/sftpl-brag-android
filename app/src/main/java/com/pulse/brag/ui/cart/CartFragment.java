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
import com.pulse.brag.data.model.datas.CartListResponeData;

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
    List<CartListResponeData> mList;

    @Inject
    CartViewModel cartViewModel;

    FragmentCartBinding mFragmentCartBinding;

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
        mFragmentCartBinding.linearNoCart.setVisibility(View.GONE);

        mList = new ArrayList<>();
    }

    public void showData() {


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


    @Override
    public void onDeleteItem(final int position, final CartListResponeData responeData) {
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                mAdapter.removeItem(responeData);
                setTotalPrice();
                if (mAdapter.getItemCount() == 0) {
                    cartViewModel.setListVisibility(false);
                }

            }
        }, 500);
    }

    @Override
    public void onQtyClick(int position, CartListResponeData responeData) {
        // TODO: 05-12-2017 limit qty (max)
        positionQty = mList.indexOf(responeData);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_PRODUCT_NAME, mList.get(positionQty).getProduct_name());
        bundle.putInt(Constants.BUNDLE_QTY, mList.get(positionQty).getQty());
        bundle.putString(Constants.BUNDLE_PRODUCT_IMG, mList.get(positionQty).getProduct_image());

        EditQtyDialogFragment dialogFragment = new EditQtyDialogFragment();
        dialogFragment.setTargetFragment(this, REQUEST_QTY);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "");
    }


    @Override
    public void onApiSuccess() {
        hideLoader();
        showData();
    }

    @Override
    public void onApiError(ApiError error) {
        hideLoader();
    }

    @Override
    public void onPlaceOrderClick() {
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
    public void getCartList(List<CartListResponeData> list) {

        mList.clear();
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "" + getString(R.string.text_s), "S", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "P", "M", "#F44336", 100, 50));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0135_2_large.jpg?v=1495697256",
                "l", "XL", "#9C27B0", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/yellow-caged_front_large.jpg?v=1480569528",
                "u", "XXL", "#2196F3", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/pink-printed-cage_front_large.jpg?v=1482476302",
                "n", "XXXL", "#FF5722", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Product Name", "XXXXXXL", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "g", "L", "#F44336", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Name", "L", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Back", "L", "#F44336", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/BRAG_NEON60459_large.jpg?v=1497980654",
                "Pe", "L", "#ffffff", 500, 1));
        mList.add(new CartListResponeData("1",
                "http://cdn.shopify.com/s/files/1/1629/9535/products/IMG_0041_large.jpg?v=1495696894",
                "Plunge Neck Cage Back T-shirt Bralette - White with Black Print & Black trims", "L", "#F44336", 500, 1));


        mAdapter = new CartListAdapter(getActivity(), mList, this);
        mFragmentCartBinding.recycleView.setAdapter(mAdapter);
        setTotalPrice();
        cartViewModel.setListVisibility(mList.isEmpty() ? false : true);

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
            total += (mList.get(i).getQty()) * (mList.get(i).getPrice());
        }
        cartViewModel.setTotal(Utility.getIndianCurrencePriceFormate(total));
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
