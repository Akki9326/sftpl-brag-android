package com.ragtagger.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.DateFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 02-01-2018.
 */


public class DataMyOrder implements Parcelable {

    private String id;
    private String userId;
    private Long createdDate;
    private boolean isActive;
    private String addressId;
    private String orderNumber;
    private double totalAmount;
    private double payableAmount;
    private DataUserAddress address;
    private Integer status;
    private DataUser user;
    private String invoiceUrl;
    private List<DataCart> cart;
    private List<DataOrderStatus> statusHistory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreateDateString() {
        return DateFormatter.convertMillisToStringDate(getCreatedDate(), DateFormatter.dd_MMM_YYYY_HH_MM_A);
    }

    public int getOrderStatesColor(Context context) {
        if (getStatus() == Constants.OrderStatus.CANCELED.ordinal()
                || getStatus() == Constants.OrderStatus.REJECTED.ordinal()) {
            return context.getResources().getColor(R.color.order_status_red);
        } else if (getStatus() == Constants.OrderStatus.PLACED.ordinal()) {
            return context.getResources().getColor(R.color.order_status_yellow);
        } else {
            return context.getResources().getColor(R.color.order_status_green);
        }
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public DataUserAddress getAddress() {
        return address;
    }

    public void setAddress(DataUserAddress address) {
        this.address = address;
    }

    public int getStatus() {
        return status != null ? status : 0;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataUser getUser() {
        return user;
    }

    public void setUser(DataUser user) {
        this.user = user;
    }

    public List<DataCart> getCart() {
        return cart;
    }

    public void setCart(List<DataCart> cart) {
        this.cart = cart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeLong(this.createdDate);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.addressId);
        dest.writeString(this.orderNumber);
        dest.writeDouble(this.totalAmount);
        dest.writeDouble(this.payableAmount);
        dest.writeParcelable(this.address, flags);
        dest.writeInt(this.status);
        dest.writeString(this.invoiceUrl);
        if (cart == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(cart);
        }
    }

    public DataMyOrder() {
    }

    protected DataMyOrder(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.createdDate = in.readLong();
        this.isActive = in.readByte() != 0;
        this.addressId = in.readString();
        this.orderNumber = in.readString();
        this.totalAmount = in.readInt();
        this.payableAmount = in.readDouble();
        try {
            this.address = in.readParcelable(DataUserAddress.class.getClassLoader());
        } catch (Exception e) {
            this.address = null;
        }

        this.status = in.readInt();
        this.invoiceUrl = in.readString();

        if (in.readByte() == 0x01) {
            cart = new ArrayList<DataCart>();
            in.readList(cart, DataCart.class.getClassLoader());
        } else {
            cart = null;
        }
    }

    public static final Parcelable.Creator<DataMyOrder> CREATOR = new Parcelable.Creator<DataMyOrder>() {
        @Override
        public DataMyOrder createFromParcel(Parcel source) {
            return new DataMyOrder(source);
        }

        @Override
        public DataMyOrder[] newArray(int size) {
            return new DataMyOrder[size];
        }
    };


    public String getFullAddressWithNewLine() {
        if (getAddress() != null) {

            String landmark;
            if (getAddress().getLandmark().isEmpty()) {
                landmark = getAddress().getLandmark();
            } else {
                landmark = getAddress().getLandmark() + " , ";
            }

            return getAddress().getAddress() + " , " + landmark + getAddress().getCity() + "\n"
                    + getAddress().getState().getText() + " - " + getAddress().getPincode();
        } else {
            return "";
        }

    }

    public String getFullAddress() {
        if (getAddress() != null) {

            String landmark;
            if (getAddress().getLandmark().isEmpty()) {
                landmark = getAddress().getLandmark();
            } else {
                landmark = getAddress().getLandmark() + " , ";
            }
            return getAddress().getAddress() + " , "
                    + landmark + getAddress().getCity() + " , " + getAddress().getState().getText() + " - "
                    + getAddress().getPincode();
        } else {
            return "";
        }

    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public List<DataOrderStatus> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<DataOrderStatus> statusHistory) {
        this.statusHistory = statusHistory;
    }

   /* public List<DataOrderStatus> getStatusHistoryList() {
        List<DataOrderStatus> mStatusList = new ArrayList<>();
        switch (Constants.OrderStatus.values()[getStatus()]) {

            case APPROVED:
                if (getStatusHistory().size() > 0)
                    mStatusList.add(new DataOrderStatus(Constants.OrderStatus.PLACED.ordinal()
                            , getStatusHistory().get(0).getCreatedDate(), Constants.OrderStatusStepper.COMPLETE.ordinal()));
                if (getStatusHistory().size() > 1)
                    mStatusList.add(new DataOrderStatus(Constants.OrderStatus.APPROVED.ordinal()
                            , getStatusHistory().get(1).getCreatedDate(), Constants.OrderStatusStepper.ACTIVE.ordinal()));
                mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DISPATCHED.ordinal(), null, Constants.OrderStatusStepper.INACTIVE.ordinal()));
                mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DELIVERED.ordinal(), null, Constants.OrderStatusStepper.INACTIVE.ordinal()));
                return mStatusList;

            case DISPATCHED:
                if (getStatusHistory().size() > 0)
                    mStatusList.add(new DataOrderStatus(Constants.OrderStatus.PLACED.ordinal()
                            , getStatusHistory().get(0).getCreatedDate(), Constants.OrderStatusStepper.COMPLETE.ordinal()));
                if (getStatusHistory().size() > 1)
                    mStatusList.add(new DataOrderStatus(Constants.OrderStatus.APPROVED.ordinal()
                            , getStatusHistory().get(1).getCreatedDate(), Constants.OrderStatusStepper.COMPLETE.ordinal()));
                if (getStatusHistory().size() > 2)
                    mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DISPATCHED.ordinal()
                            , getStatusHistory().get(2).getCreatedDate(), Constants.OrderStatusStepper.ACTIVE.ordinal()));
                mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DELIVERED.ordinal(), null, Constants.OrderStatusStepper.INACTIVE.ordinal()));
                return mStatusList;

            default:
                return getStatusHistory();
        }*/

    public List<DataOrderStatus> getStatusHistoryList() {
        List<DataOrderStatus> mStatusList = new ArrayList<>();

        List<DataOrderStatus> mStatusListHistory = new ArrayList<>();
        mStatusListHistory.addAll(getStatusHistory());
        Collections.sort(mStatusListHistory);
        switch (Constants.OrderStatus.values()[getStatus()]) {


            case APPROVED:
                for (int i = 0; i < mStatusListHistory.size(); i++) {
                    if (i == mStatusListHistory.size() - 1)
                        mStatusList.add(new DataOrderStatus(Constants.OrderStatus.APPROVED.ordinal(),
                                mStatusListHistory.get(i).getCreatedDate(), Constants.OrderStatusStepper.ACTIVE.ordinal()));
                    else
                        mStatusList.add(mStatusListHistory.get(i));
                }
                mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DISPATCHED.ordinal(), null, Constants.OrderStatusStepper.INACTIVE.ordinal()));
                mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DELIVERED.ordinal(), null, Constants.OrderStatusStepper.INACTIVE.ordinal()));
                return mStatusList;

            case DISPATCHED:
                for (int i = 0; i < mStatusListHistory.size(); i++) {
                    if (i == mStatusListHistory.size() - 1)
                        mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DISPATCHED.ordinal(),
                                mStatusListHistory.get(i).getCreatedDate(), Constants.OrderStatusStepper.ACTIVE.ordinal()));
                    else
                        mStatusList.add(mStatusListHistory.get(i));
                }
                mStatusList.add(new DataOrderStatus(Constants.OrderStatus.DELIVERED.ordinal(), null, Constants.OrderStatusStepper.INACTIVE.ordinal()));
                return mStatusList;

            default:
                return mStatusListHistory;
        }

    }
}
