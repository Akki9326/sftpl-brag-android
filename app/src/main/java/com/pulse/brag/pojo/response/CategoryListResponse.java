package com.pulse.brag.pojo.response;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.pojo.datas.CategoryListResponseData;
import com.pulse.brag.pojo.datas.UserData;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 16-10-2017.
 */


public class CategoryListResponse {

    private boolean status;
    private Integer errorCode;
    private String message;
    private List<CategoryListResponseData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryListResponseData> getData() {
        return data;
    }

    public void setData(List<CategoryListResponseData> data) {
        this.data = data;
    }
}
