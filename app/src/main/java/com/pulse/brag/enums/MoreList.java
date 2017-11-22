package com.pulse.brag.enums;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 13-11-2017.
 */


public enum MoreList {
    MY_ORDER(1),
    PRIVACY_POLICY(2),
    TERMS_AND(3),
    CHANGE_PASS(4),
    LOGOUT(5);



    MoreList (int i)
    {
        this.type = i;
    }

    private int type;

    public int getNumericType()
    {
        return type;
    }
}
