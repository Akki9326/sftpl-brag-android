package com.pulse.brag.pojo.requests;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 11-12-2017.
 */


public class ChangeMobileNumberRequest {
    private String mobileNumber;
    private String password;

    public ChangeMobileNumberRequest(String mobileNumber, String password) {
        this.mobileNumber = mobileNumber;
        this.password = password;
    }
}
