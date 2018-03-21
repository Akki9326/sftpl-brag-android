package com.ragtagger.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentOrderDetailBinding;
import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerActivity;
import com.ragtagger.brag.ui.order.orderdetail.adapter.OrderCartListAdapter;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.AppPermission;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.FileUtils;
import com.ragtagger.brag.utils.Utility;


import java.io.File;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public class OrderDetailFragment extends CoreFragment<FragmentOrderDetailBinding, OrderDetailViewModel> implements OrderDetailNavigator {

    final int REQUEST_PERMISSION = 01;

    @Inject
    OrderDetailViewModel orderDetailViewModel;
    FragmentOrderDetailBinding mFragmentOrderDetailBinding;

    String orderId;
    DataMyOrder mData;
    int downloadIdOne;

    public static OrderDetailFragment newInstance(DataMyOrder data) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_ORDER_DATA, data);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetailFragment newInstance(String orderId) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_ORDER_ID, orderId);
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

        orderDetailViewModel.updateIsLoading(true);

        Animation up = AnimationUtils.loadAnimation(getContext(), R.anim.right_out);
        Animation down = AnimationUtils.loadAnimation(getContext(), R.anim.left_in);
        mFragmentOrderDetailBinding.imageviewDownload.setInAnimation(up);
        mFragmentOrderDetailBinding.imageviewDownload.setOutAnimation(down);
        mFragmentOrderDetailBinding.imageviewDownload.setBackgroundResource(R.drawable.ic_download);

        mFragmentOrderDetailBinding.recycleview.setHasFixedSize(true);
        mFragmentOrderDetailBinding.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentOrderDetailBinding.recycleview.setMotionEventSplittingEnabled(false);
        mFragmentOrderDetailBinding.recycleview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mFragmentOrderDetailBinding.recycleview.setNestedScrollingEnabled(false);


        checkInternetAncGetDetails();
    }


    @Override
    public void setUpToolbar() {
        if (getActivity() instanceof MainActivity)
        ((MainActivity) getActivity()).showToolbar(true, false, false, getString(R.string.toolbar_label_order_detail));
        else if (getActivity() instanceof NotificationHandlerActivity)
            ((NotificationHandlerActivity)getActivity()).setUpToolbar();
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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void onApiReorderSuccess() {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), null, getString(R.string.msg_reorder_success), null);
    }

    @Override
    public void onApiReorderError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void onDownloadInvoice() {
        if (Utility.isConnection(getContext())) {
            if (!AppPermission.isPermissionRequestRequired(getActivity()
                    , new String[]{"android.permission.READ_EXTERNAL_STORAGE"}
                    , REQUEST_PERMISSION)) {

                downloadOrOpenFile();
            }
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    private void checkInternetAncGetDetails() {
        if (mData != null) {
            showData();
        } else if (orderId != null) {
            if (Utility.isConnection(getActivity())) {
                showProgress();
                orderDetailViewModel.getOrderDetails(orderId);
            } else {
                AlertUtils.showAlertMessage(getActivity(), 0, null, null);
            }
        }
    }

    private void downloadOrOpenFile() {
        String path = Environment.getExternalStorageDirectory().toString();
        String pathWithFolder = Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name);

        //make Brag folder
        Utility.makeFolder(path, getString(R.string.app_name));


        if (FileUtils.isFileExist(pathWithFolder + "/" + "example.pdf")) {
            //open file
            Utility.fileIntentHandle(getActivity(), new File(pathWithFolder + "/" + "example.pdf"));
        } else {
            //download file
            downloadfileFromPRDownloader(pathWithFolder);
        }
    }

    private void downloadfileFromPRDownloader(String path) {

        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.pause(downloadIdOne);
            return;
        }

        mFragmentOrderDetailBinding.imageviewDownload.setEnabled(false);


        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.resume(downloadIdOne);
            return;
        }

        downloadIdOne = PRDownloader.download(orderDetailViewModel.getInvoiveUrl(), path, "example.pdf")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        mFragmentOrderDetailBinding.imageviewDownload.setBackgroundResource(R.drawable.ic_download_close);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        mFragmentOrderDetailBinding.imageviewDownload.setBackgroundResource(R.drawable.ic_download);
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        mFragmentOrderDetailBinding.imageviewDownload.setBackgroundResource(R.drawable.ic_download);
                        mFragmentOrderDetailBinding.progressBarDownload.setProgress(0);
                        downloadIdOne = 0;
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        mFragmentOrderDetailBinding.progressBarDownload.setProgress((int) progressPercent);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        mFragmentOrderDetailBinding.imageviewDownload.setBackgroundResource(R.drawable.ic_download);
                        Toast.makeText(getContext(), getString(R.string.msg_download_complate), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Error error) {
                        mFragmentOrderDetailBinding.imageviewDownload.setBackgroundResource(R.drawable.ic_download);
                        Toast.makeText(getContext(), getString(R.string.error_download_file), Toast.LENGTH_SHORT).show();
                        mFragmentOrderDetailBinding.progressBarDownload.setProgress(0);
                        downloadIdOne = 0;
                    }
                });
    }


    @Override
    public void onReorderClick() {
        if (Utility.isConnection(getActivity())) {
            showData();
            orderDetailViewModel.reOrderAPI(mData.getId());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void onOrderDetails(DataMyOrder orderDetails) {
        mData = orderDetails;
        showData();
    }

    @Override
    public void onNoOrderData() {
        mData = null;
        showData();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_detail;
    }


    public void showData() {
        orderDetailViewModel.updateIsLoading(false);
        if (mData != null) {
            orderDetailViewModel.updateHasData(true);
            orderDetailViewModel.updateOrderId(mData.getOrderNumber());
            orderDetailViewModel.updateAddress(mData.getUser().getFullAddressWithNewLine());
            orderDetailViewModel.updateFullName(mData.getUser().getFullName());
            orderDetailViewModel.updateIsOrderApprove(mData.getStatus() == Constants.OrderStatus.DELIVERED.ordinal());
            orderDetailViewModel.updateOrderState(Constants.OrderStatus.getOrderStatusLabel(getContext(), mData.getStatus()));
            orderDetailViewModel.updateTotalCartNum(mData.getCart().size());
            orderDetailViewModel.updateOrderStateDate(mData.getCreateDateString());
            orderDetailViewModel.setTotal(Utility.getIndianCurrencyPriceFormatWithComma((int) mData.getTotalAmount()));
            mFragmentOrderDetailBinding.recycleview.setAdapter(new OrderCartListAdapter(getActivity(), mData.getCart()));

            mFragmentOrderDetailBinding.textviewStatus.setTextColor(mData.getOrderStatesColor(getContext()));
        } else {
            orderDetailViewModel.updateHasData(false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    switch (permission) {
                        case "android.permission.READ_EXTERNAL_STORAGE":
                            if (PackageManager.PERMISSION_GRANTED == grantResult) {
                                downloadOrOpenFile();
                            }
                            break;
                    }
                }
                break;
        }
    }
}
