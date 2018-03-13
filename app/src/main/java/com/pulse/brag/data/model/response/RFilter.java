package com.pulse.brag.data.model.response;

import com.pulse.brag.data.model.datas.DataFilter;
import com.pulse.brag.data.model.datas.DataProductList;

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
