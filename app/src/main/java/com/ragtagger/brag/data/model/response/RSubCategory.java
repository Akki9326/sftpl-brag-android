package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataCart;
import com.ragtagger.brag.data.model.datas.DataSubCategory;

import java.util.List;

public class RSubCategory {
    private boolean status;
    private Integer errorCode;
    private String message;
    private DataSubCategory data;

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

    public DataSubCategory getData() {
        return data;
    }

    public void setData(DataSubCategory data) {
        this.data = data;
    }
}
