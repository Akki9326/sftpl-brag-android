package com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public interface StateDialogNavigator extends CoreNavigator {

    void onClose();

    void onStateSelect(DataState data);
}
