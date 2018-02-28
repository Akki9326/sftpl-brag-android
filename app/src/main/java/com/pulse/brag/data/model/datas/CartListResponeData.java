package com.pulse.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 04-12-2017.
 */


public class CartListResponeData implements Parcelable {


    private String id;
    private String product_image;
    private String product_name;
    private String size;
    private String color;
    private int price;
    private int qty;

    public CartListResponeData(String id, String product_image, String product_name, String size, String color, int price, int qty) {
        this.id = id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSize() {
        return size==null?"":size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriceWithSym() {
        return Utility.getIndianCurrencePriceFormate(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.product_image);
        dest.writeString(this.product_name);
        dest.writeString(this.size);
        dest.writeString(this.color);
        dest.writeInt(this.price);
        dest.writeInt(this.qty);
    }

    protected CartListResponeData(Parcel in) {
        this.id = in.readString();
        this.product_image = in.readString();
        this.product_name = in.readString();
        this.size = in.readString();
        this.color = in.readString();
        this.price = in.readInt();
        this.qty = in.readInt();
    }

    public static final Parcelable.Creator<CartListResponeData> CREATOR = new Parcelable.Creator<CartListResponeData>() {
        @Override
        public CartListResponeData createFromParcel(Parcel source) {
            return new CartListResponeData(source);
        }

        @Override
        public CartListResponeData[] newArray(int size) {
            return new CartListResponeData[size];
        }
    };
}
