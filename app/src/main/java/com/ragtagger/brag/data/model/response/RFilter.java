package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataFilter;

/**
 * Created by alpesh.rathod on 3/12/2018.
 */

public class RFilter {

    private boolean status;
    private Integer errorCode;
    private String message;
    private DataFilter data;

    public boolean isStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public DataFilter getData() {
        return data;
    }
}
