package com.ragtagger.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 23-02-2018.
 */


public class DataOrderDetail {

    private String id;
    private String order_id;
    private DataAddress address;
    private DataOrderProductDetail dataOrderProductDetail;

    public DataOrderDetail(String id, String order_id, String product_name
            , String product_image_url, String product_qty, String product_price) {
        this.id = id;
        this.order_id = order_id;
        this.dataOrderProductDetail = new DataOrderProductDetail(product_name, product_image_url, product_qty, product_price);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataOrderProductDetail getDataOrderProductDetail() {
        return dataOrderProductDetail;
    }

    public void setDataOrderProductDetail(DataOrderProductDetail dataOrderProductDetail) {
        this.dataOrderProductDetail = dataOrderProductDetail;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public DataAddress getAddress() {
        return address;
    }

    public void setAddress(DataAddress address) {
        this.address = address;
    }
}
