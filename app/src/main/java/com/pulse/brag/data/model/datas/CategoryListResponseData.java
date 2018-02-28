package com.pulse.brag.data.model.datas;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by nikhil.vadoliya on 15-01-2018.
 */


public class CategoryListResponseData implements Comparable<CategoryListResponseData>,Parcelable {
    private String id;
    private String optionName;
    private String url;
    private int optionOrderNo;
    private List<CategoryListResponseData> childs = Collections.emptyList();

    public List<CategoryListResponseData> getChild() {
        return childs;
    }

    public void setChild(List<CategoryListResponseData> child) {
        this.childs = child;
    }

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
    }

    public String getOptionName() {
        return optionName == null ? "" : optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOptionOrderNo() {
        return optionOrderNo;
    }

    public void setOptionOrderNo(int optionOrderNo) {
        this.optionOrderNo = optionOrderNo;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.optionName);
        dest.writeString(this.url);
        dest.writeInt(this.optionOrderNo);
        dest.writeList(this.childs);
    }

    protected CategoryListResponseData(Parcel in) {
        this.id = in.readString();
        this.optionName = in.readString();
        this.url = in.readString();
        this.optionOrderNo = in.readInt();
        this.childs = new ArrayList<CategoryListResponseData>();
        in.readList(this.childs, CategoryListResponseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<CategoryListResponseData> CREATOR = new Parcelable.Creator<CategoryListResponseData>() {
        @Override
        public CategoryListResponseData createFromParcel(Parcel source) {
            return new CategoryListResponseData(source);
        }

        @Override
        public CategoryListResponseData[] newArray(int size) {
            return new CategoryListResponseData[size];
        }
    };
}
