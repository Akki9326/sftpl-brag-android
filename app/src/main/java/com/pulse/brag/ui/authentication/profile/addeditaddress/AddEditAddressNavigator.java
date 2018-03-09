package com.pulse.brag.ui.authentication.profile.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.StateData;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public interface AddEditAddressNavigator extends CoreNavigator {

    void onAddOrUpdateAddress();

    boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent);

    void onOpenStateListDialog();

    void onApiSuccessState(List<StateData> data);

    void onApiErrorState(ApiError error);


}
