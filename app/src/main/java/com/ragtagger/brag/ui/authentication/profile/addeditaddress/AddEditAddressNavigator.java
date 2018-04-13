package com.ragtagger.brag.ui.authentication.profile.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.view.KeyEvent;
import android.widget.TextView;

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public interface AddEditAddressNavigator extends CoreNavigator {

    boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent);

    void setUserProfile();

    void onApiErrorUserProfile(ApiError error);

    void performClickOnAdd();

    void performClickUpdate();

    void noInternetAlert();

    void invalidAddOrUpdateForm(String msg);

    void validAddAddressForm();

    void validUpdateAddressForm();

    void performClickState();

    void setStateList(List<DataState> data);
}
