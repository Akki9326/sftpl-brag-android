package com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.BaseObservable;

import com.ragtagger.brag.data.model.datas.DataState;

/**
 * Created by nikhil.vadoliya on 08-03-2018.
 */


public class StateItemViewModel extends BaseObservable{

    Context context;
    int position;
    DataState responeData;

    public StateItemViewModel(Context context, DataState responeData) {
        this.context = context;
        this.responeData=new DataState();
        this.responeData=responeData;
        notifyChange();
    }

    public void setResponeData(DataState responeData){
        this.responeData=responeData;
        notifyChange();
    }


    public String getStateName() {
        return responeData.getText();
    }
}
