package com.ragtagger.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.ragtagger.brag.data.model.datas.DataOrderStatus;
import com.ragtagger.brag.databinding.FragmentOrderDetailBinding;
import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerActivity;
import com.ragtagger.brag.ui.order.orderdetail.adapter.OrderCartListAdapter;
import com.ragtagger.brag.ui.order.orderstatushistory.MainStepperAdapter;
import com.ragtagger.brag.ui.order.orderstatushistory.OrderStatusFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.FileUtils;
import com.ragtagger.brag.utils.ToastUtils;
import com.ragtagger.brag.utils.Utility;


import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    int downloadId;
    String fileName;

    MainStepperAdapter mainStepperAdapter;

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

    }

    @Override
    public void afterViewCreated() {
        if (getArguments().containsKey(Constants.BUNDLE_ORDER_ID))
            orderId = getArguments().getString(Constants.BUNDLE_ORDER_ID);
        if (getArguments().containsKey(Constants.BUNDLE_ORDER_DATA))
            mData = getArguments().getParcelable(Constants.BUNDLE_ORDER_DATA);

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

        checkInternetAndGetDetails();
    }


    @Override
    public void setUpToolbar() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).showToolbar(true, false, false, getString(R.string.toolbar_label_order_detail));
        else if (getActivity() instanceof NotificationHandlerActivity)
            ((NotificationHandlerActivity) getActivity()).showPushToolbar(true, false, getString(R.string.toolbar_label_order_detail));
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
        AlertUtils.showAlertMessage(getActivity(), getString(R.string.msg_reorder_success));
    }

    @Override
    public void onApiReorderError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void onDownloadInvoice() {

        fileName = mData.getOrderNumber() + "_invoice.pdf";
        String pathWithFolder = Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name);
        if (checkAndRequestPermissions()) {
            if (FileUtils.isFileExist(pathWithFolder + "/" + fileName)) {
                Utility.fileIntentHandle(getActivity(), new File(pathWithFolder +
                        "/" + fileName));
            } else {
                if (Utility.isConnection(getContext())) {
                    downloadFile();
                } else {
                    AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                }
            }
        }

    }

    @Override
    public void onApiSuccessDownload() {
        hideProgress();
        onDownloadInvoice();
    }

    @Override
    public void onApiErrorDownload(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    private void checkInternetAndGetDetails() {
        if (mData != null) {
            showData();
        } else if (orderId != null) {
            if (Utility.isConnection(getActivity())) {
                // put handle when user come from notification list
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showProgress();
                        orderDetailViewModel.getOrderDetails(orderId);
                    }
                }, 500);

            } else {
                AlertUtils.showAlertMessage(getActivity(), 0, null, null);
            }
        } else {
            AlertUtils.showAlertMessage(getActivity(), 1, null, null);
        }
    }

    private void downloadFile() {
        String path = Environment.getExternalStorageDirectory().toString();
        String pathWithFolder = Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name);
        Utility.makeFolder(path, getString(R.string.app_name));
        if (mData.getInvoiceUrl() != null && !mData.getInvoiceUrl().isEmpty()) {
            showProgress();
            orderDetailViewModel.downloadInvoice(mData.getInvoiceUrl(), pathWithFolder, fileName);
        } else {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.msg_invoice_not_generated));
        }
//        downloadfileFromPRDownloader(pathWithFolder);

    }

    private void downloadfileFromPRDownloader(String path) {

        if (Status.RUNNING == PRDownloader.getStatus(downloadId)) {
            PRDownloader.pause(downloadId);
            return;
        }

        mFragmentOrderDetailBinding.imageviewDownload.setEnabled(false);


        if (Status.PAUSED == PRDownloader.getStatus(downloadId)) {
            PRDownloader.resume(downloadId);
            return;
        }

        downloadId = PRDownloader.download(orderDetailViewModel.getInvoiveUrl(), path, fileName)
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
                        downloadId = 0;
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
                        downloadId = 0;
                    }
                });
    }


    @Override
    public void onReorderClick() {
        String message = getString(R.string.msg_you_order_delived_address) + "\n" + mData.getFullAddressWithNewLine();
        AlertUtils.showAlertMessageCancelOk(getContext(), message, new AlertUtils.IDialogListenerCancelAndOk() {
            @Override
            public void onCancelClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onOkClick(Dialog dialog) {
                dialog.dismiss();
                if (Utility.isConnection(getActivity())) {
                    showProgress();
                    orderDetailViewModel.reOrderAPI(mData.getId());
                } else {
                    AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                }
            }
        });
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
    public void onCancelledClick() {
        if (Utility.isConnection(getActivity())) {
            showProgress();
            orderDetailViewModel.onCancelOrder(mData.getId());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void onApiCancelSuccess() {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), getString(R.string.msg_cancel_order));
        //update order detail of states and cancel button
        orderDetailViewModel.updateOrderState(Constants.OrderStatus.getOrderStatusLabel(getContext(), Constants.OrderStatus.CANCELED.ordinal()));
        orderDetailViewModel.setIsOrderPlaced(false);
        mFragmentOrderDetailBinding.textviewStatus.setTextColor(getResources().getColor(R.color.order_status_red));
        mData.setStatus(Constants.OrderStatus.CANCELED.ordinal());
        mData.getStatusHistory().add(new DataOrderStatus(Constants.OrderStatus.CANCELED.ordinal()
                , (new Timestamp(System.currentTimeMillis())).getTime()
                , Constants.OrderStatusStepper.COMPLETE.ordinal()));
        orderStatueStepperDataSet();
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_ORDER);
        intent.putExtra(Constants.BUNDLE_IS_ORDER_CANCEL, true);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onApiCancelError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);

    }

    @Override
    public void onStatusClick() {
//        ((MainActivity) getActivity()).pushFragments(new OrderStatusFragment(), true, true);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_detail;
    }


    public void showData() {
        orderDetailViewModel.updateIsLoading(false);
        if (isAdded())
            if (mData != null) {
                if (mData.getUser() != null && mData.getAddress() != null) {
                    orderDetailViewModel.updateHasData(true);
                    orderDetailViewModel.updateOrderId(mData.getOrderNumber());
                    orderDetailViewModel.updateAddress(mData.getFullAddressWithNewLine());
                    orderDetailViewModel.updateFullName(mData.getUser().getFullName());
                    orderDetailViewModel.updateIsOrderApprove(mData.getStatus() == Constants.OrderStatus.APPROVED.ordinal()
                            || mData.getStatus() == Constants.OrderStatus.DELIVERED.ordinal()
                            || mData.getStatus() == Constants.OrderStatus.DISPATCHED.ordinal());
                    orderDetailViewModel.updateOrderState(Constants.OrderStatus.getOrderStatusLabel(getActivity(), mData.getStatus()));
                    orderDetailViewModel.updateTotalCartNum(mData.getCart().size());
                    orderDetailViewModel.updateOrderStateDate(mData.getCreateDateString());
                    orderDetailViewModel.setTotal(Utility.getIndianCurrencyPriceFormatWithComma(mData.getTotalAmount()));
                    orderDetailViewModel.setMobilenum(mData.getUser().getMobileNumber());
                    orderDetailViewModel.setIsOrderPlaced(mData.getStatus() == Constants.OrderStatus.PLACED.ordinal());
                    orderDetailViewModel.setTotalPayable(Utility.getIndianCurrencyPriceFormatWithComma(
                            (mData.getStatus() == Constants.OrderStatus.APPROVED.ordinal() || mData.getStatus() == Constants.OrderStatus.DISPATCHED.ordinal() || mData.getStatus() == Constants.OrderStatus.DELIVERED.ordinal()) ? (mData.getPayableAmount()) :
                                    mData.getTotalAmount()));
                    mFragmentOrderDetailBinding.recycleview.setAdapter(new OrderCartListAdapter(getActivity(), mData.getCart()));

                    mFragmentOrderDetailBinding.textviewStatus.setTextColor(mData.getOrderStatesColor(getActivity()));

                    orderStatueStepperDataSet();


                } else {
                    orderId = mData.getId();
                    mData = null;
                    checkInternetAndGetDetails();
                }
            } else {
                orderDetailViewModel.updateHasData(false);
            }
    }

    private void orderStatueStepperDataSet() {

        //order status stepper
        mainStepperAdapter = new MainStepperAdapter(getActivity(), mData.getStatusHistoryList());
        mFragmentOrderDetailBinding.orderStatus.stepperList.setAdapter(mainStepperAdapter);
        //set dynamic listview height
        ViewGroup.LayoutParams params = mFragmentOrderDetailBinding.orderStatus.stepperList.getLayoutParams();
        params.height = (int) (Utility.getListViewHeight(mFragmentOrderDetailBinding.orderStatus.stepperList)
                + Utility.dpToPx(getContext(), 15));
        mFragmentOrderDetailBinding.orderStatus.stepperList.setLayoutParams(params);

        switch (Constants.OrderStatus.values()[mData.getStatus()]) {
            case PLACED:
                mainStepperAdapter.jumpTo(1);
                break;
            case APPROVED:
                mainStepperAdapter.jumpTo(2);
                break;
            case DISPATCHED:
                mainStepperAdapter.jumpTo(3);
                break;
            default:
                mainStepperAdapter.jumpTo(mData.getStatusHistory().size());

        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onPermissionDenied(int request) {
        super.onPermissionDenied(request);
    }

    @Override
    public void onPermissionGranted(int request) {
        super.onPermissionGranted(request);
        onDownloadInvoice();
    }

    private boolean checkAndRequestPermissions() {
        boolean hasPermissionReadData = hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean hasPermissionWriteData = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (!hasPermissionReadData) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!hasPermissionWriteData) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermission(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Constants.IPermissionRequestCode.REQ_STORAGE_READ_AND_WRITE);
            return false;
        }
        return true;
    }

}
