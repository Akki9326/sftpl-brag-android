package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataNotificationList;

/**
 * Created by alpesh.rathod on 3/20/2018.
 */

public class RNotificationList {
    private boolean status;
    private Integer errorCode;
    private String message;
    private DataNotificationList data;

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

    public DataNotificationList getData() {
        return data;
    }

    public void setData(DataNotificationList data) {
        this.data = data;
    }
}
