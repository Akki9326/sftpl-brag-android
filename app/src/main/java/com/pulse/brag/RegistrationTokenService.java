package com.pulse.brag; /**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.PreferencesManager;

/**
 * Created by nikhil.vadoliya on 15-11-2017.
 */


public class RegistrationTokenService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationTokenService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        if (intent.hasExtra(Constants.BUNDLE_DEVICE_TOKEN))
            PreferencesManager.getInstance().setDeviceToken(intent.getStringExtra(Constants.BUNDLE_DEVICE_TOKEN));

    }
}
