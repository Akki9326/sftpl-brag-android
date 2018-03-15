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

/**
 * Created by nikhil.vadoliya on 09-03-2018.
 */


public class DataUserAddress implements Parcelable {
    private String id;
    private String address;
    private String landmark;
    private String city;
    private int pincode;
    private DataState state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public DataState getState() {
        return state;
    }

    public void setState(DataState state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeString(this.landmark);
        dest.writeString(this.city);
        dest.writeInt(this.pincode);
        dest.writeParcelable(this.state, flags);
    }

    public DataUserAddress() {
    }

    protected DataUserAddress(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.landmark = in.readString();
        this.city = in.readString();
        this.pincode = in.readInt();
        this.state = in.readParcelable(DataState.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataUserAddress> CREATOR = new Parcelable.Creator<DataUserAddress>() {
        @Override
        public DataUserAddress createFromParcel(Parcel source) {
            return new DataUserAddress(source);
        }

        @Override
        public DataUserAddress[] newArray(int size) {
            return new DataUserAddress[size];
        }
    };
}
