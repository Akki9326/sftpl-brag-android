package com.ragtagger.brag.ui.core;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */

/*
* This class use in Recycleview implement by MVVM and this class provide ViewDataBinding obj.*/
public abstract class CoreViewHolder extends RecyclerView.ViewHolder {

    public CoreViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}
