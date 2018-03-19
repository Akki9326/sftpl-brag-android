package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataProductList;

/**
 * Created by alpesh.rathod on 3/7/2018.
 */

public class RProductList {

    private boolean status;
    private Integer errorCode;
    private String message;
    private DataProductList data;

    public boolean isStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public DataProductList getData() {
        return data;
    }
}
