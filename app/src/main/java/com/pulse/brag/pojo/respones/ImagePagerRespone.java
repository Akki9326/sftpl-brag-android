package com.pulse.brag.pojo.respones;

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
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class ImagePagerRespone implements Parcelable {
    String url;
    String id;

    public ImagePagerRespone(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        dest.writeString(this.id);
    }

    protected ImagePagerRespone(Parcel in) {
        this.url = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<ImagePagerRespone> CREATOR = new Parcelable.Creator<ImagePagerRespone>() {
        @Override
        public ImagePagerRespone createFromParcel(Parcel source) {
            return new ImagePagerRespone(source);
        }

        @Override
        public ImagePagerRespone[] newArray(int size) {
            return new ImagePagerRespone[size];
        }
    };
}
