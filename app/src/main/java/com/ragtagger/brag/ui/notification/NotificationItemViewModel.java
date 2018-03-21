package com.ragtagger.brag.ui.notification;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.ragtagger.brag.data.model.datas.DataCart;
import com.ragtagger.brag.data.model.datas.DataNotificationList;
import com.ragtagger.brag.ui.cart.CartItemViewModel;
import com.ragtagger.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 20-03-2018.
 */


public class NotificationItemViewModel extends BaseObservable {

    Context context;
    DataNotificationList responeData;
    OnItemClick onItemClick;
    int position;

    public NotificationItemViewModel(Context context, DataNotificationList responeData, int position
            , OnItemClick onItemClick) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
    }

    public String getTitle() {
        return responeData.getTitle();
    }

    public String getDescription() {
        return responeData.getBody();
    }

    public void onItemClick(View v) {
        onItemClick.onItemClick(position, responeData);
    }

    public boolean isReaded() {
        return responeData.isAttended();
    }

    public interface OnItemClick {
        public void onItemClick(int position, DataNotificationList responeData);

    }
}
