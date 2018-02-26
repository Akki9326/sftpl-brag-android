package com.pulse.brag.pojo.datas;


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
import android.text.format.DateUtils;

import com.pulse.brag.R;
import com.pulse.brag.utils.DateFormatter;
import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 02-01-2018.
 */


public class MyOrderListResponeData implements Parcelable {

    private String id;
    private String order_id;
    private String product_name;
    private String product_image_url;
    private String product_qty;
    private String product_price;
    private int status;
    private Long date;


    public MyOrderListResponeData(String id, String order_id, String product_name,
                                  String product_image_url, String product_qty, String product_price, int status, long date) {
        this.id = id;
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_image_url = product_image_url;
        this.product_qty = product_qty;
        this.product_price = product_price;
        this.status = status;
        this.date = date;
    }

    public MyOrderListResponeData(String id, String order_id, String product_name, String product_image_url, String product_qty, String product_price, int status) {
        this.id = id;
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_image_url = product_image_url;
        this.product_qty = product_qty;
        this.product_price = product_price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProductQtyWithLabel(Activity activity) {
        return "" + activity.getString(R.string.label_qty_s) + " " + product_qty;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProductPriceWithSym() {
        return Utility.getIndianCurrencePriceFormate(Integer.parseInt(product_price));
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDateFromTimestamp() {
        return DateFormatter.convertMillisToStringDate(getDate()*1000, DateFormatter.dd_MMM_YYYY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.order_id);
        dest.writeString(this.product_name);
        dest.writeString(this.product_image_url);
        dest.writeString(this.product_qty);
        dest.writeString(this.product_price);
        dest.writeInt(this.status);
        dest.writeLong(this.date);
    }

    protected MyOrderListResponeData(Parcel in) {
        this.id = in.readString();
        this.order_id = in.readString();
        this.product_name = in.readString();
        this.product_image_url = in.readString();
        this.product_qty = in.readString();
        this.product_price = in.readString();
        this.status = in.readInt();
        this.date = in.readLong();
    }

    public static final Parcelable.Creator<MyOrderListResponeData> CREATOR = new Parcelable.Creator<MyOrderListResponeData>() {
        @Override
        public MyOrderListResponeData createFromParcel(Parcel source) {
            return new MyOrderListResponeData(source);
        }

        @Override
        public MyOrderListResponeData[] newArray(int size) {
            return new MyOrderListResponeData[size];
        }
    };
}
