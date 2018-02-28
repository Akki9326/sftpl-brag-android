package com.pulse.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 23-02-2018.
 */


public class OrderDetailResponeData {

    private String id;
    private String order_id;
    private String product_name;
    private String product_image_url;
    private String product_qty;
    private String product_price;
    private AddressRespones address;

    public OrderDetailResponeData(String id, String order_id, String product_name
            , String product_image_url, String product_qty, String product_price) {
        this.id = id;
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_image_url = product_image_url;
        this.product_qty = product_qty;
        this.product_price = product_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProductPriceWithSym() {
        return Utility.getIndianCurrencePriceFormate(Integer.parseInt(getProduct_price()));
    }

    public AddressRespones getAddress() {
        return address;
    }

    public void setAddress(AddressRespones address) {
        this.address = address;
    }
}
