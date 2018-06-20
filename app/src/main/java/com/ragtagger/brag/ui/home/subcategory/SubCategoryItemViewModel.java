package com.ragtagger.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.view.View;

import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.data.model.datas.DataSubCategory;

/**
 * Created by nikhil.vadoliya on 27-02-2018.
 */


public class SubCategoryItemViewModel {

    Context context;
    DataSubCategory.DataObject responeData;
    OnItemClickListener onItemClick;
    int position;


    public SubCategoryItemViewModel(Context context, int position, DataSubCategory.DataObject responeData, OnItemClickListener onItemClick) {
        this.context = context;
        this.responeData = responeData;
        this.onItemClick = onItemClick;
        this.position = position;
    }

    public void setResponeData(DataSubCategory.DataObject responeData) {
        this.responeData = responeData;
        notifyAll();
    }


    public void onItemClick(View view) {
        onItemClick.onItemClick(position, responeData);
    }

    public String getId() {
        return responeData.getId();
    }

    public String getOptionName() {
        return responeData.getOptionName();
    }

    public String getUrl() {
        return responeData.getUrl();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, DataSubCategory.DataObject responeData);
    }
}
