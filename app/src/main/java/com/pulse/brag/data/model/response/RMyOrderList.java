package com.pulse.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.datas.DataMyOrder;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 02-01-2018.
 */


public class RMyOrderList {

    private String q;
    private Object medianType;
    private Integer count;
    private Integer pageSize;
    private List<DataMyOrder> objects = null;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Object getMedianType() {
        return medianType;
    }

    public void setMedianType(Object medianType) {
        this.medianType = medianType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataMyOrder> getObjects() {
        return objects;
    }

    public void setObjects(List<DataMyOrder> objects) {
        this.objects = objects;
    }
}
