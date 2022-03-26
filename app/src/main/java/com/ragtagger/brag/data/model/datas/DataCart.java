package com.ragtagger.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikhil.vadoliya on 04-12-2017.
 */


public class DataCart implements Parcelable {


    private String id;
    private int quantity;
    private long createdDate;
    private DataCartItem item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public DataCartItem getItem() {
        return item;
    }

    public void setItem(DataCartItem item) {
        this.item = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.item.getItemId());
        dest.writeInt(this.quantity);
        dest.writeLong(this.createdDate);
        dest.writeParcelable(this.item, flags);
    }

    public DataCart() {
    }

    protected DataCart(Parcel in) {
        this.id = in.readString();
        String itemId = in.readString();
        this.quantity = in.readInt();
        this.createdDate = in.readLong();
        this.item = in.readParcelable(DataCartItem.class.getClassLoader());
        this.item.setItemId(itemId);
    }

    public static final Parcelable.Creator<DataCart> CREATOR = new Parcelable.Creator<DataCart>() {
        @Override
        public DataCart createFromParcel(Parcel source) {
            return new DataCart(source);
        }

        @Override
        public DataCart[] newArray(int size) {
            return new DataCart[size];
        }
    };
}
