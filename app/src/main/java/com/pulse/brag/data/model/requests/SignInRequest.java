package com.pulse.brag.data.model.requests;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class SignInRequest {

    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String addPassword;

    public SignInRequest(String title, String firstname, String lastname, String email, String mobilenumber, String password) {
        this.title = title;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.mobileNumber = mobilenumber;
        this.addPassword = password;
    }
}
