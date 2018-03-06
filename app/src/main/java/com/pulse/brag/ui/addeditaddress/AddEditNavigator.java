package com.pulse.brag.ui.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public interface AddEditNavigator extends CoreNavigator {

    void onAddOrUpdateAddress();

    boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent);
}
