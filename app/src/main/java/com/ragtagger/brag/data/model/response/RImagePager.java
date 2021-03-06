package com.ragtagger.brag.data.model.response;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.ragtagger.brag.BR;

/**
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class RImagePager extends BaseObservable implements Parcelable {

    @Bindable
    String url;
    @Bindable
    String id;

    public RImagePager(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
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

    protected RImagePager(Parcel in) {
        this.url = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<RImagePager> CREATOR = new Parcelable.Creator<RImagePager>() {
        @Override
        public RImagePager createFromParcel(Parcel source) {
            return new RImagePager(source);
        }

        @Override
        public RImagePager[] newArray(int size) {
            return new RImagePager[size];
        }
    };

}
