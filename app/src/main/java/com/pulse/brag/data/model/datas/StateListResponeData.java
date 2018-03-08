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
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public class StateListResponeData implements Parcelable {
    private String id;
    private String text;

    public StateListResponeData() {
    }

    public StateListResponeData(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.text);
    }

    protected StateListResponeData(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<StateListResponeData> CREATOR = new Parcelable.Creator<StateListResponeData>() {
        @Override
        public StateListResponeData createFromParcel(Parcel source) {
            return new StateListResponeData(source);
        }

        @Override
        public StateListResponeData[] newArray(int size) {
            return new StateListResponeData[size];
        }
    };
}
