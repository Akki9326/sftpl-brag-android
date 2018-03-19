package com.ragtagger.brag.data.model.requests;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 03-11-2017.
 */


public class QLogin {

    private String mobileNumber;
    private String password;

    public QLogin(String mobileNumber, String password) {
        this.mobileNumber = mobileNumber;
        this.password = password;
    }
}
