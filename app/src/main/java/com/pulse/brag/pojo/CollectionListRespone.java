package com.pulse.brag.pojo;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class CollectionListRespone {

    String url;
    String name;
    String id;
    String price;

    public CollectionListRespone(String url, String name, String id, String price) {
        this.url = url;
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceWithSym() {
        return "Rs " + price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
