package com.ragtagger.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 18-12-2017.
 */


public class DataNotificationList {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("medianType")
    @Expose
    private java.lang.Object medianType;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("pageSize")
    @Expose
    private int pageSize;
    @SerializedName("objects")
    @Expose
    private List<Notification> objects = null;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public java.lang.Object getMedianType() {
        return medianType;
    }

    public void setMedianType(java.lang.Object medianType) {
        this.medianType = medianType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Notification> getObjects() {
        return objects;
    }

    public void setObjects(List<Notification> objects) {
        this.objects = objects;
    }


    public static class Notification {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("body")
        @Expose
        private String body;
        @SerializedName("notificationType")
        @Expose
        private int notificationType;
        @SerializedName("whatId")
        @Expose
        private java.lang.Object whatId;
        @SerializedName("isAttended")
        @Expose
        private boolean isAttended;

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

        public int getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(int notificationType) {
            this.notificationType = notificationType;
        }

        public java.lang.Object getWhatId() {
            return whatId;
        }

        public void setWhatId(java.lang.Object whatId) {
            this.whatId = whatId;
        }

        public boolean isIsAttended() {
            return isAttended;
        }

        public void setIsAttended(boolean isAttended) {
            this.isAttended = isAttended;
        }
    }
}
