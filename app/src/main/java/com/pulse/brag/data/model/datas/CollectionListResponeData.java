package com.pulse.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import java.util.List;

/**
 * Created by nikhil.vadoliya on 16-01-2018.
 */


public class CollectionListResponeData {
    private List<CategoryListResponseData.Category> categories;
    private List<CategoryListResponseData.Banners> banners;


    public List<CategoryListResponseData.Category> getCategories() {
        return categories;
    }

    public List<CategoryListResponseData.Banners> getBanners() {
        return banners;
    }

    /* private String id;
    private String optionName;
    private String url;
    private int optionOrderNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOptionOrderNo() {
        return optionOrderNo;
    }

    public void setOptionOrderNo(int optionOrderNo) {
        this.optionOrderNo = optionOrderNo;
    }

    @Override
    public int compareTo(@NonNull CollectionListResponeData o) {
        if (optionOrderNo > o.getOptionOrderNo()) {
            return 1;
        } else if (optionOrderNo < o.getOptionOrderNo()) {
            return -1;
        } else {
            return 0;
        }
    }*/
}
