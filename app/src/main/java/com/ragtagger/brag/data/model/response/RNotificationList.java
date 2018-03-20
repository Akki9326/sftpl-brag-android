package com.ragtagger.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataNotificationList;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 20-03-2018.
 */


public class RNotificationList {
    private String q;
    private String medianType;
    private int count;
    private int pageSize;
    private List<DataNotificationList> objects;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getMedianType() {
        return medianType;
    }

    public void setMedianType(String medianType) {
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

    public List<DataNotificationList> getObjects() {
        return objects;
    }

    public void setObjects(List<DataNotificationList> objects) {
        this.objects = objects;
    }
}
