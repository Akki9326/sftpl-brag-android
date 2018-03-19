package com.ragtagger.brag.ui.cart.editquantity;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.text.Editable;

/**
 * Created by nikhil.vadoliya on 20-02-2018.
 */


public interface EditQtyDialogNavigator {

    void onDoneClick();
    void afterTextChanged(Editable s);
}
