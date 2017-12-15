package com.pulse.brag.pojo.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 05-12-2017.
 */


public class PriceListData {
    private String product;
    private int price;
    private String muliplecation;

    public PriceListData(String product, int price, String muliplecation) {
        this.product = product;
        this.price = price;
        this.muliplecation = muliplecation;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMuliplecation() {
        return muliplecation;
    }

    public void setMuliplecation(String muliplecation) {
        this.muliplecation = muliplecation;
    }
}
