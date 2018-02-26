package com.pulse.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentOrderDetailBinding;
import com.pulse.brag.pojo.datas.MyOrderListResponeData;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public class OrderDetailFragment extends CoreFragment<FragmentOrderDetailBinding, OrderDetailViewModel> implements OrderDetailNavigator {

    String orderId;
    MyOrderListResponeData mData;

    @Inject
    OrderDetailViewModel orderDetailViewModel;
    FragmentOrderDetailBinding mFragmentOrderDetailBinding;

    int downloadIdOne;

    public static OrderDetailFragment newInstance(String id, MyOrderListResponeData data) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_ORDER_ID, id);
        args.putParcelable(Constants.BUNDLE_ORDER_DATA, data);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderDetailViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        if (getArguments().containsKey(Constants.BUNDLE_ORDER_ID))
            orderId = getArguments().getString(Constants.BUNDLE_ORDER_ID);
        if (getArguments().containsKey(Constants.BUNDLE_ORDER_DATA))
            mData = getArguments().getParcelable(Constants.BUNDLE_ORDER_DATA);
    }

    @Override
    public void afterViewCreated() {
        mFragmentOrderDetailBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mFragmentOrderDetailBinding.baseLayout);
//        checkInternet();
        showData();

    }

    private void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            orderDetailViewModel.getOrderDetail();
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void setUpToolbar() {
        ((MainActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_order_detail));
    }

    @Override
    public OrderDetailViewModel getViewModel() {
        return orderDetailViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        showData();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void onDownloadInvoice() {
        if(Utility.isConnection(getContext())){
            mFragmentOrderDetailBinding.progressBarDownload.setVisibility(View.VISIBLE);
            downloadFile();
        }else {
            AlertUtils.showAlertMessage(getActivity(),0,null);
        }

        Toast.makeText(getActivity(), "On Download Invoice", Toast.LENGTH_SHORT).show();
    }

    private void downloadFile() {
    }

    @Override
    public void onReorderClick() {
        Toast.makeText(getActivity(), "Reorder", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_detail;
    }


    public void showData() {
        orderDetailViewModel.updateOrderId(mData.getOrder_id());
        orderDetailViewModel.updateProductImage(mData.getProduct_image_url());
        orderDetailViewModel.updateProductName(mData.getProduct_name());
        orderDetailViewModel.updateProductPrice(mData.getProductPriceWithSym());
        orderDetailViewModel.updateProductQty(mData.getProduct_qty());
        orderDetailViewModel.updateAddress("" + getString(R.string.text_s));
        orderDetailViewModel.updateCity("Ahmedabad");
        orderDetailViewModel.updateStateWithPincode("Gujarat-380015");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }
}
