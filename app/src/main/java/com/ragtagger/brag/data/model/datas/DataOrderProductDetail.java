package com.ragtagger.brag.data.model.datas;

import com.ragtagger.brag.utils.Utility;

/**
 * Created by Deep.adeshra on 23-03-2022.
 */

public class DataOrderProductDetail {
    private String product_name;
    private String product_image_url;
    private String product_qty;
    private String product_price;

    public DataOrderProductDetail(String product_name, String product_image_url, String product_qty, String product_price) {
        this.product_name = product_name;
        this.product_image_url = product_image_url;
        this.product_qty = product_qty;
        this.product_price = product_price;
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
        return Utility.getIndianCurrencyPriceFormat(Integer.parseInt(getProduct_price()));
    }
}
