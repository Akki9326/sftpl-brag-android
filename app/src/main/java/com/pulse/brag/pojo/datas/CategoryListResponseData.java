package com.pulse.brag.pojo.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import com.pulse.brag.BR;


/**
 * Created by nikhil.vadoliya on 15-01-2018.
 */


public class CategoryListResponseData extends BaseObservable implements Comparable<CategoryListResponseData> {
    @Bindable
    private String id;
    @Bindable
    private String optionName;

    @Bindable
    private String url;
    @Bindable
    private int optionOrderNo;

    public CategoryListResponseData(String id, String optionName, String url, int optionOrderNo) {
        this.id = id;
        this.optionName = optionName;
        this.url = url;
        this.optionOrderNo = optionOrderNo;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public String getOptionName() {
        return optionName == null ? "" : optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
        notifyPropertyChanged(BR.optionName);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    public int getOptionOrderNo() {
        return optionOrderNo;
    }

    public void setOptionOrderNo(int optionOrderNo) {
        this.optionOrderNo = optionOrderNo;
        notifyPropertyChanged(BR.optionOrderNo);
    }


    @Override
    public int compareTo(@NonNull CategoryListResponseData o) {
        if (optionOrderNo > o.getOptionOrderNo()) {
            return 1;
        } else if (optionOrderNo < o.getOptionOrderNo()) {
            return -1;
        } else {
            return 0;
        }
    }

    /*private ItemViewModelListener listener;

    public CategoryListResponseData(ItemViewModelListener listener) {
        this.listener = listener;
    }

    public void onClickItem(){
        listener.onClickItem();
    }

    public interface ItemViewModelListener {
        void onClickItem();
    }*/
}
