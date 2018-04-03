package com.ragtagger.brag.ui.order.orderdetail;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.response.ROrderDetail;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 22-02-2018.
 */


public class OrderDetailViewModel extends CoreViewModel<OrderDetailNavigator> {


    ObservableField<String> orderId = new ObservableField<>();
    ObservableField<String> address = new ObservableField<>();
    ObservableField<String> fullName = new ObservableField<>();
    ObservableField<String> orderState = new ObservableField<>();
    ObservableField<String> date = new ObservableField<>();
    ObservableField<Boolean> isOrderApprove = new ObservableField<>();
    ObservableField<Boolean> isOrderPlaced = new ObservableField<>();
    ObservableField<String> mobilenum = new ObservableField<>();
    ObservableField<Integer> orderStateColor = new ObservableField<>();
    ObservableField<String> total = new ObservableField<>();
    ObservableField<String> totalPayable = new ObservableField<>();
    ObservableField<Integer> listSize = new ObservableField<>();
    String itemsLable;

    ObservableField<Boolean> isLoading = new ObservableField<>();
    ObservableField<Boolean> hasData = new ObservableField<>();
    ObservableField<Boolean> isVisiableInvoice = new ObservableField<>();

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public void updateIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public ObservableField<Boolean> getHasData() {
        return hasData;
    }

    public void updateHasData(boolean hasData) {
        this.hasData.set(hasData);
    }

    public OrderDetailViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getOrderId() {
        return orderId;
    }

    public void updateOrderId(String orderId) {
        this.orderId.set(orderId);
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public void updateAddress(String address) {
        this.address.set(address);
    }


    public View.OnClickListener onDownloadInvoice() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onDownloadInvoice();
            }
        };
    }

    public View.OnClickListener onReorder() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onReorderClick();
            }
        };
    }

    public View.OnClickListener onCancelled() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onCancelledClick();
            }
        };
    }

    public void onCancelOrder(String id) {
        Call<RGeneralData> rGeneralDataCall = getDataManager().cancelOrder(id);
        rGeneralDataCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData rGeneralData, Headers headers) {
                if (rGeneralData.isStatus()) {
                    getNavigator().onApiCancelSuccess();
                } else {
                    getNavigator().onApiCancelError(new ApiError(rGeneralData.getErrorCode(), rGeneralData.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiCancelError(t);
            }
        });
    }

    public String getInvoiveUrl() {
        return "https://www.tutorialspoint.com/android/android_tutorial.pdf";
    }

    public void updateFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public ObservableField<String> getFullName() {
        return fullName;
    }


    public void updateIsOrderApprove(boolean isApprove) {
        this.isOrderApprove.set(isApprove);
    }

    public ObservableField<Boolean> isApprvoe() {
        return isOrderApprove;
    }

    public void updateOrderState(String orderState) {
        this.orderState.set(orderState);
    }

    public ObservableField<String> getOrderState() {
        return orderState;
    }

    public void updateOrderStateDate(String date) {
        this.date.set(date);
    }

    public ObservableField<String> getOrderStateDate() {
        return date;
    }

    public void updateTotalCartNum(int size) {
        listSize.set(size);
        if (size > 1) {
            itemsLable = "items";
        } else {
            itemsLable = "item";
        }
    }

    public ObservableField<Integer> getCartSize() {
        return listSize;
    }

    public String getItemsLable() {
        return itemsLable;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public ObservableField<String> getTotalPrice() {
        return total;
    }

    public void setTotalPayable(String total) {
        this.totalPayable.set(total);
    }

    public ObservableField<String> getTotalPricePayable() {
        return totalPayable;
    }

    public ObservableField<String> getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum.set(mobilenum);
    }


    void reOrderAPI(String orderId) {
        Call<RGeneralData> call = getDataManager().reOrder(orderId);
        call.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiReorderSuccess();
                } else {
                    getNavigator().onApiReorderError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiReorderError(t);
            }
        });
    }

    void getOrderDetails(String orderId) {
        Call<ROrderDetail> call = getDataManager().getOrderDetail(orderId);
        call.enqueue(new ApiResponse<ROrderDetail>() {
            @Override
            public void onSuccess(ROrderDetail rOrderDetail, Headers headers) {
                if (rOrderDetail.isStatus()) {
                    getNavigator().onApiSuccess();
                    if (rOrderDetail.getData() != null) {
                        getNavigator().onOrderDetails(rOrderDetail.getData());
                    } else {
                        getNavigator().onNoOrderData();
                    }
                } else {
                    getNavigator().onApiError(new ApiError(rOrderDetail.getErrorCode(), rOrderDetail.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {

            }
        });

    }

    public ObservableField<Boolean> getIsOrderPlaced() {
        return isOrderPlaced;
    }

    public void setIsOrderPlaced(boolean isOrderPlaced) {
        this.isOrderPlaced.set(isOrderPlaced);
    }

    public ObservableField<Boolean> getIsVisiableInvoice() {
        return isVisiableInvoice;
    }

    public void setIsVisiableInvoice(ObservableField<Boolean> isVisiableInvoice) {
        this.isVisiableInvoice = isVisiableInvoice;
    }

    public void downloadInvoice(String url, final String filePath, final String fileName) {
        Call<ResponseBody> bodyCall = getDataManager().downloadInvoice(url);
        bodyCall.enqueue(new ApiResponse<ResponseBody>() {
            @Override
            public void onSuccess(final ResponseBody responseBody, Headers headers) {

                writeResponseBodyToDisk(responseBody, filePath, fileName);

            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiErrorDownload(t);
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String filePath, String filename) {
        try {


            File file = new File(filePath + "/" + filename);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded / 1024 * 1024 + " of " + fileSize / 1024 * 1024);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                getNavigator().onApiErrorDownload(new ApiError(5001, ""));
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
                getNavigator().onApiSuccessDownload();
            }
        } catch (IOException e) {
            return false;
        }
    }

    public View.OnClickListener onStatusClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onStatusClick();
            }
        };
    }
}
