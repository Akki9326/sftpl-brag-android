package com.pulse.brag.data.model.response;

import com.pulse.brag.data.model.datas.DataAddToCart;
import com.pulse.brag.data.model.datas.DataProductList;

import java.util.List;

/**
 * Created by alpesh.rathod on 3/9/2018.
 */

public class RAddToCart {

    private boolean status;
    private Integer errorCode;
    private String message;
    private List<DataAddToCart> data;

    public boolean isStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public List<DataAddToCart> getData() {
        return data;
    }
}
