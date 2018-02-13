package com.pulse.brag.pojo.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 04-12-2017.
 */


public class CartListResponeData {


    private String id;
    private String product_image;
    private String product_name;
    private String size;
    private String color;
    private int price;
    private int qty;

    public CartListResponeData(String id, String product_image, String product_name, String size, String color, int price, int qty) {
        this.id = id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSize() {
        return size==null?"":size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriceWithSym() {
        return Utility.getIndianCurrencePriceFormate(price);
    }
}
