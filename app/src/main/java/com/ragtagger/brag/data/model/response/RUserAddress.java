package com.ragtagger.brag.data.model.response;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataUserAddress;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 13-03-2018.
 */


public class RUserAddress {
    private boolean status;
    private Integer errorCode;
    private String message;
    private List<DataUserAddress> addresses;

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

    public List<DataUserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DataUserAddress> addresses) {
        this.addresses = addresses;
    }
}
