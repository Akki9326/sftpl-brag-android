package com.pulse.brag.pojo;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import java.util.List;

/**
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class DummeyRespone {

    private Long per_page;
    private int total;
    private Long total_pages;
    private List<DummeyDataRespone> data = null;

    public Long getPer_page() {
        return per_page;
    }

    public void setPer_page(Long per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Long total_pages) {
        this.total_pages = total_pages;
    }

    public List<DummeyDataRespone> getData() {
        return data;
    }

    public void setData(List<DummeyDataRespone> data) {
        this.data = data;
    }
}
