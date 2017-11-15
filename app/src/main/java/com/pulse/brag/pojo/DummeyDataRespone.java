package com.pulse.brag.pojo;


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
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class DummeyDataRespone implements Parcelable {
    private int id;
    private String first_name;
    private String last_name;
    private String avatar;

    public DummeyDataRespone(int id, String first_name, String last_name, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static Creator<DummeyDataRespone> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.avatar);
    }

    public DummeyDataRespone() {
    }

    protected DummeyDataRespone(Parcel in) {
        this.id = in.readInt();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.avatar = in.readString();
    }

    public static final Parcelable.Creator<DummeyDataRespone> CREATOR = new Parcelable.Creator<DummeyDataRespone>() {
        @Override
        public DummeyDataRespone createFromParcel(Parcel source) {
            return new DummeyDataRespone(source);
        }

        @Override
        public DummeyDataRespone[] newArray(int size) {
            return new DummeyDataRespone[size];
        }
    };
}
