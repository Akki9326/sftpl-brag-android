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

    public static class BannerList implements Parcelable {

        private String id;
        private String purpose;
        private String url;
        private String createdBy;
        private long createdDate;
        private String lastModifiedBy;
        private long lastModifiedDate;
        private boolean isActive;
        private boolean isDeleted;
        private int tabType;
        private List<CategoryListResponseData.BannerList> childs = Collections.emptyList();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public String getLastModifiedBy() {
            return lastModifiedBy;
        }

        public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
        }

        public long getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(long lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public int getTabType() {
            return tabType;
        }

        public void setTabType(int tabType) {
            this.tabType = tabType;
        }

        public List<CategoryListResponseData.BannerList> getChilds() {
            return childs;
        }

        public void setChilds(List<CategoryListResponseData.BannerList> childs) {
            this.childs = childs;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.purpose);
            dest.writeString(this.url);
            dest.writeString(this.createdBy);
            dest.writeLong(this.createdDate);
            dest.writeString(this.lastModifiedBy);
            dest.writeLong(this.lastModifiedDate);
            dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isDeleted ? (byte) 1 : (byte) 0);
            dest.writeInt(this.tabType);
            dest.writeList(this.childs);
        }

        public BannerList() {
        }

        protected BannerList(Parcel in) {
            this.id = in.readString();
            this.purpose = in.readString();
            this.url = in.readString();
            this.createdBy = in.readString();
            this.createdDate = in.readLong();
            this.lastModifiedBy = in.readString();
            this.lastModifiedDate = in.readLong();
            this.isActive = in.readByte() != 0;
            this.isDeleted = in.readByte() != 0;
            this.tabType = in.readInt();
            this.childs = new ArrayList<CategoryListResponseData.BannerList>();
            in.readList(this.childs, CategoryListResponseData.BannerList.class.getClassLoader());
        }

        public static final Parcelable.Creator<BannerList> CREATOR = new Parcelable.Creator<BannerList>() {
            @Override
            public BannerList createFromParcel(Parcel source) {
                return new BannerList(source);
            }

            @Override
            public BannerList[] newArray(int size) {
                return new BannerList[size];
            }
        };


    }

}
