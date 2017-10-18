package com.pulse.brag.interfaces;

/**
 * Copyright (c) 2015 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 *
 * Class Usage : Base interface which includes basic methods
 *               which are must implemented in all activities/fragments.
 */

public interface BaseInterface {
    void setToolbar(); // toolbar details set
    void initializeData(); // initialize all views inside this method

    void setListeners(); // set listeners for all views

    void showData(); // display data into view
}
