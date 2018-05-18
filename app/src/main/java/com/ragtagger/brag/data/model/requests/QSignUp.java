package com.ragtagger.brag.data.model.requests;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 08-11-2017.
 */


public class QSignUp {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String addPassword;
    private int userType;
    private String gstin;
    private DataState state;
    private DataSaleType saleTypeModel;
    private DataChannel channelModel;
    private List<QAddAddress> addresses;

    public QSignUp(String firstname, String lastname, String email, String mobilenumber, String password, int userType, String gstin, DataState state, DataSaleType salesType, DataChannel channel) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.mobileNumber = mobilenumber;
        this.addPassword = password;
        this.userType = userType;
        this.gstin = gstin;
        this.state = state;
        this.channelModel = channel;
        this.saleTypeModel = salesType;
    }

    public QSignUp(String firstname, String lastname, String email, String mobilenumber,
                   String password, int userType, String gstin, DataState state,
                   DataSaleType salesType, DataChannel channel, QAddAddress mAddAddresses) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.mobileNumber = mobilenumber;
        this.addPassword = password;
        this.userType = userType;
        this.gstin = gstin;
        this.state = state;
        this.channelModel = channel;
        this.saleTypeModel = salesType;
        this.addresses=new ArrayList<>();
        this.addresses.add(mAddAddresses);
    }
}
