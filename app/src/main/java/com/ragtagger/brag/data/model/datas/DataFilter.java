package com.ragtagger.brag.data.model.datas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpesh.rathod on 3/12/2018.
 */

public class DataFilter {

    private String q;
    private Filter filter;
    private String orderByEnum;
    private int count;
    private List<String> loadedItems = null;
    private String category;
    private String subCategory;
    private String seasonCode;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getOrderByEnum() {
        return orderByEnum;
    }

    public void setOrderByEnum(String orderByEnum) {
        this.orderByEnum = orderByEnum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getLoadedItems() {
        return loadedItems;
    }

    public void setLoadedItems(List<String> loadedItems) {
        this.loadedItems = loadedItems;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public static class Filter implements Parcelable {

        private List<ColorCode> colorCodes = null;
        private List<String> sizeCodes = null;

        public List<ColorCode> getColorCodes() {
            return colorCodes;
        }

        public void setColorCodes(List<ColorCode> colorCodes) {
            this.colorCodes = colorCodes;
        }

        public List<String> getSizeCodes() {
            return sizeCodes;
        }

        public void setSizeCodes(List<String> sizeCodes) {
            this.sizeCodes = sizeCodes;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.colorCodes);
            dest.writeStringList(this.sizeCodes);
        }

        public Filter() {
        }

        protected Filter(Parcel in) {
            this.colorCodes = new ArrayList<ColorCode>();
            in.readList(this.colorCodes, ColorCode.class.getClassLoader());
            this.sizeCodes = in.createStringArrayList();
        }

        public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
            @Override
            public Filter createFromParcel(Parcel source) {
                return new Filter(source);
            }

            @Override
            public Filter[] newArray(int size) {
                return new Filter[size];
            }
        };
    }

    public static class ColorCode {

        private String color;
        private String hash;
        private String variant;
        private boolean selected;

        private String key;

        public ColorCode(String color, String hash, String variant) {
            this.color = color;
            this.hash = hash;
            this.variant = variant;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getVariant() {

            return variant;
        }

        public void setVariant(String variant) {
            this.variant = variant;
        }

        public String getKey() {
            return color + variant;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class SizeCode {
        String size;
        boolean selected;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public SizeCode(String size, boolean selected) {
            this.size = size;
            this.selected = selected;
        }
    }

}
