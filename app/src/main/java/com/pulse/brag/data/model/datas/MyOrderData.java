package com.pulse.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.pulse.brag.R;
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.utils.DateFormatter;
import com.pulse.brag.utils.Utility;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 02-01-2018.
 */


public class MyOrderData implements Parcelable {

    private String id;
    private String userId;
    private Long createdDate;
    private boolean isActive;
    private String addressId;
    private String orderNumber;
    private double totalAmount;
    private double payableAmount;
    private UserAddress address;
    private int status;
    private UserData user;
    private List<CartData> cart;

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

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public List<CartData> getCart() {
        return cart;
    }

    public void setCart(List<CartData> cart) {
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
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.cart);
    }

    public MyOrderData() {
    }

    protected MyOrderData(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.createdDate = in.readLong();
        this.isActive = in.readByte() != 0;
        this.addressId = in.readString();
        this.orderNumber = in.readString();
        this.totalAmount = in.readInt();
        this.payableAmount = in.readDouble();
        this.address = in.readParcelable(UserAddress.class.getClassLoader());
        this.status = in.readInt();
        this.user = in.readParcelable(UserData.class.getClassLoader());
        this.cart = in.createTypedArrayList(CartData.CREATOR);
    }

    public static final Parcelable.Creator<MyOrderData> CREATOR = new Parcelable.Creator<MyOrderData>() {
        @Override
        public MyOrderData createFromParcel(Parcel source) {
            return new MyOrderData(source);
        }

        @Override
        public MyOrderData[] newArray(int size) {
            return new MyOrderData[size];
        }
    };


    public String getFullAddressWithNewLine() {
        if (getAddress() != null) {
            return getAddress().getAddress() + " , " + getAddress().getLandmark() + " , "
                    + getAddress().getCity() + "\n"
                    + getAddress().getState().getText() + " - " + getAddress().getPincode();
        } else {
            return "";
        }

    }

    public String getFullAddress() {
        if (getAddress() != null) {
            return getAddress().getAddress() + " , " + getAddress().getLandmark() + " , "
                    + getAddress().getCity() + " , " + getAddress().getState().getText() + " - "
                    + getAddress().getPincode();
        } else {
            return "";
        }

    }
}
