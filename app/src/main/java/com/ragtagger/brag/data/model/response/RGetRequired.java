package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataGetRequired;
import com.ragtagger.brag.data.model.datas.DataMyOrder;

public class RGetRequired {
    private boolean status;
    private Integer errorCode;
    private String message;
    private DataGetRequired data;

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

    public DataGetRequired getData() {
        return data;
    }

    public void setData(DataGetRequired data) {
        this.data = data;
    }
}
