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


public class CategoryListResponseData {

    private List<CategoryList> categories;
    private List<BannerList> banners;


    public List<CategoryList> getCategories() {
        return categories;
    }

    public List<BannerList> getBanners() {
        return banners;
    }

    public static class CategoryList implements Comparable<CategoryList>, Parcelable {
        private String id;
        private String optionName;
        private String url;
        private int optionOrderNo;
        private List<CategoryList> childs = Collections.emptyList();

        public List<CategoryList> getChild() {
            return childs;
        }

        public void setChild(List<CategoryList> child) {
            this.childs = child;
        }

        public CategoryList(String id, String optionName, String url, int optionOrderNo) {
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
        public int compareTo(@NonNull CategoryList o) {
            if (optionOrderNo > o.getOptionOrderNo()) {
                return 1;
            } else if (optionOrderNo < o.getOptionOrderNo()) {
                return -1;
            } else {
                return 0;
            }
        }

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

        protected CategoryList(Parcel in) {
            this.id = in.readString();
            this.optionName = in.readString();
            this.url = in.readString();
            this.optionOrderNo = in.readInt();
            this.childs = new ArrayList<CategoryList>();
            in.readList(this.childs, CategoryListResponseData.class.getClassLoader());
        }

        public static final Parcelable.Creator<CategoryList> CREATOR = new Parcelable.Creator<CategoryList>() {
            @Override
            public CategoryList createFromParcel(Parcel source) {
                return new CategoryList(source);
            }

            @Override
            public CategoryList[] newArray(int size) {
                return new CategoryList[size];
            }
        };

    }

    public static class BannerList implements Comparable<CategoryList>, Parcelable {

        protected BannerList(Parcel in) {
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<BannerList> CREATOR = new Creator<BannerList>() {
            @Override
            public BannerList createFromParcel(Parcel in) {
                return new BannerList(in);
            }

            @Override
            public BannerList[] newArray(int size) {
                return new BannerList[size];
            }
        };

        @Override
        public int compareTo(@NonNull CategoryList categoryList) {
            return 0;
        }
    }

}
