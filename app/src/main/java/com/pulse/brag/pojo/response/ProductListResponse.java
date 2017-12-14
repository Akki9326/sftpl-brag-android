package com.pulse.brag.pojo.response;

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

import com.pulse.brag.R;

/**
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class ProductListResponse implements Parcelable {

    String url;
    String name;
    String id;
    String price;

    public ProductListResponse(String url, String name, String id, String price) {
        this.url = url;
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceWithSym(Context mContext) {
        return mContext.getResources().getString(R.string.text_rs) + " " + price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.price);
    }

    protected ProductListResponse(Parcel in) {
        this.url = in.readString();
        this.name = in.readString();
        this.id = in.readString();
        this.price = in.readString();
    }

    public static final Parcelable.Creator<ProductListResponse> CREATOR = new Parcelable.Creator<ProductListResponse>() {
        @Override
        public ProductListResponse createFromParcel(Parcel source) {
            return new ProductListResponse(source);
        }

        @Override
        public ProductListResponse[] newArray(int size) {
            return new ProductListResponse[size];
        }
    };
}
