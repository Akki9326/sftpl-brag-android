package com.pulse.brag.ui.cart.placeorder;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.requests.QPlaceOrder;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.ui.cart.adapter.PlaceOrderCartListAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentPlaceOrderBinding;
import com.pulse.brag.data.model.datas.CartData;
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
    List<CartData> mList;

    public static PlaceOrderFragment newInstance(List<CartData> cartList) {

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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mUpdateProfile, new IntentFilter(Constants.LOCALBROADCAST_UPDATE_PROFILE));
    }

    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPlaceOrderViewModel.getUserProfile();
                }
            }, 500);
        }
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideKeyboard();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void onPlaceOrder() {
        if (mPlaceOrderViewModel.getDataManager().getUserData().getAddresses() == null
                || mPlaceOrderViewModel.getDataManager().getUserData().getAddresses().isEmpty()) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_add_address));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mPlaceOrderViewModel.placeOrderAPI(new QPlaceOrder());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);

        }

    }

    @Override
    public void onEditAddress() {
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, Constants.ProfileIsFrom.ADD_EDIT_ADDRESS.ordinal());
        intent.putExtra(Constants.BUNDLE_ADDRESS, mPlaceOrderViewModel.getUserAddress());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void onAddAddress() {
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, Constants.ProfileIsFrom.ADD_EDIT_ADDRESS.ordinal());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void onPriceLabelClick() {
        mFragmentPlaceOrderBinding.nestedScroll.smoothScrollTo(0, (mFragmentPlaceOrderBinding.viewDummy).getTop());

    }

    @Override
    public void onApiSuccessPlaceOrder() {
        hideProgress();
    }

    @Override
    public void onApiErrorPlaceOrder(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
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
        checkInternet();
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

        mAdapter = new PlaceOrderCartListAdapter(getActivity(), mList, this);
        mFragmentPlaceOrderBinding.recycleview.setAdapter(mAdapter);
        setTotalPrice();


    }

    @Override
    public void onQtyClick(int position, CartData responeData) {

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
            total += (mList.get(i).getQuantity()) * (mList.get(i).getItem().getUnitPrice());
        }
        mPlaceOrderViewModel.setTotal(Utility.getIndianCurrencyPriceFormatWithComma(total));
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

    private BroadcastReceiver mUpdateProfile = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.BUNDLE_IS_ADDRESS_UPDATE)) {
                mPlaceOrderViewModel.setAddress(mPlaceOrderViewModel.getDataManager().getUserData()
                        .getFullAddressWithNewLine());
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mUpdateProfile);
        super.onPause();
    }
}
