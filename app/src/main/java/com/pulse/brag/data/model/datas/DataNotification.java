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
 * Created by nikhil.vadoliya on 15-11-2017.
 */


public class DataNotification implements Parcelable {

    private String id;
    private String title;
    private String body;
    private String contentId;
    private int notificationType;
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.contentId);
        dest.writeInt(this.notificationType);
        dest.writeString(this.category);
    }

    public DataNotification() {
    }

    protected DataNotification(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.body = in.readString();
        this.contentId = in.readString();
        this.notificationType = in.readInt();
        this.category = in.readString();
    }

    public static final Parcelable.Creator<DataNotification> CREATOR = new Parcelable.Creator<DataNotification>() {
        @Override
        public DataNotification createFromParcel(Parcel source) {
            return new DataNotification(source);
        }

        @Override
        public DataNotification[] newArray(int size) {
            return new DataNotification[size];
        }
    };
}
