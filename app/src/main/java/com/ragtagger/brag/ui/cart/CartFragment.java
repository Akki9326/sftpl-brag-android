package com.ragtagger.brag.ui.cart;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.ui.cart.adapter.CartListAdapter;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentCartBinding;
import com.ragtagger.brag.ui.cart.placeorder.PlaceOrderFragment;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.cart.editquantity.EditQtyDialogFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.data.model.datas.DataCart;

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
    List<DataCart> mList;

    @Inject
    CartViewModel cartViewModel;

    FragmentCartBinding mFragmentCartBinding;
    DataCart mCartItemDeleteData;
    String mEditQtyItemNo;
    String mEditQtyItemId;
    int updateItemQtyNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel.setNavigator(this);
    }


    private void checkInternetAndCallApi(boolean showProgress) {
        if (isAdded())
            if (Utility.isConnection(getActivity())) {
                cartViewModel.setNoInternet(false);
                if (showProgress)
                    showProgress();
                cartViewModel.getCartData();
            } else {
                mFragmentCartBinding.swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cartViewModel.setNoInternet(true);
                    }
                }, 150);
            }
    }


    @Override
    public void beforeViewCreated() {
    }

    @Override
    public void afterViewCreated() {
        mFragmentCartBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentCartBinding.baseLayout);
        cartViewModel.setNoInternet(false);
        initializeData();
        checkInternetAndCallApi(true);

        mFragmentCartBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternetAndCallApi(true);
            }
        });

    }

    @Override
    public void setUpToolbar() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).showToolbar(true, false, false, getResources().getString(R.string.toolbar_label_cart));
        else if (getActivity() instanceof NotificationHandlerActivity)
            ((NotificationHandlerActivity)getActivity()).showPushToolbar(true,false,getResources().getString(R.string.toolbar_label_cart));
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
            updateItemQtyNum = data.getIntExtra(Constants.BUNDLE_QTY, 0);
            //if old and new qty not same
            if (updateItemQtyNum != mList.get(positionQty).getQuantity()) {
                if (Utility.isConnection(getActivity())) {
                    showProgress();
                    cartViewModel.editQty(mEditQtyItemId, mEditQtyItemNo, updateItemQtyNum);
                } else {
                    AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                }
            }
        }
    }


    @Override
    public void onDeleteItem(final int position, final DataCart responeData) {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            mCartItemDeleteData = responeData;
            cartViewModel.removeFromCart(responeData.getItemId());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void onQtyClick(int position, DataCart responeData) {
        // TODO: 05-12-2017 limit qty (max)
        positionQty = mList.indexOf(responeData);
        mEditQtyItemNo = mList.get(positionQty).getItemId();
        mEditQtyItemId = mList.get(positionQty).getId();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_QTY, mList.get(positionQty).getQuantity());
        bundle.putInt(Constants.BUNDLE_NUM_STOCK, mList.get(positionQty).getItem().getStockData());
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
        if (error.getHttpCode() == 0 && error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode) {
            cartViewModel.setNoInternet(true);
            return;
        }
        cartViewModel.setNoInternet(false);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void onContinuesClick() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).pushFragments(PlaceOrderFragment.newInstance(mList), true, true);
        else if (getActivity() instanceof NotificationHandlerActivity)
            ((NotificationHandlerActivity) getActivity()).pushFragments(PlaceOrderFragment.newInstance(mList), true, true);

    }

    @Override
    public void onPriceClick() {
        //mFragmentCartBinding.recycleView.scrollToPosition(mList.size() - 1);
    }

    @Override
    public void swipeRefresh() {
        mFragmentCartBinding.swipeRefreshLayout.setRefreshing(true);
        checkInternetAndCallApi(false);
    }

    @Override
    public void getCartList(List<DataCart> list) {

        mList = new ArrayList<>();
        mList.addAll(list);
        mAdapter = new CartListAdapter(getActivity(), mList, this);
        mFragmentCartBinding.recycleView.setAdapter(mAdapter);
        setTotalPrice();
        cartViewModel.setListVisibility(!mList.isEmpty());
    }

    @Override
    public void onSuccessDeleteFromAPI() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                mAdapter.removeItem(mCartItemDeleteData);
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).updateCartNum();
                else if (getActivity() instanceof NotificationHandlerActivity)
                    ((NotificationHandlerActivity) getActivity()).updateCartNum();

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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void onSuccessEditQtyFromAPI() {
        mAdapter.qtyUpdate(positionQty, updateItemQtyNum);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                mAdapter.notifyItemChanged(positionQty);
                setTotalPrice();
            }
        }, 1000);
    }

    @Override
    public void onErrorEditQtyFromAPI(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
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

        double total = 0;
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
