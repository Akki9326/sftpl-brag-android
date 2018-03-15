package com.pulse.brag.data.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pulse.brag.data.model.datas.DataFilter;
import com.pulse.brag.data.model.datas.DataProductList;

import java.util.List;

/**
 * Created by alpesh.rathod on 3/8/2018.
 */

public class QProductList {
    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("objects")
    @Expose
    private List<Object> objects = null;
    @SerializedName("filter")
    @Expose
    private Filter filter;
    @SerializedName("orderByEnum")
    @Expose
    private int orderByEnum;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("loadedItems")
    @Expose
    private List<String> loadedItems = null;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subCategory")
    @Expose
    private String subCategory;
    @SerializedName("seasonCode")
    @Expose
    private String seasonCode;


    public void setQ(String q) {
        this.q = q;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setOrderByEnum(int orderByEnum) {
        this.orderByEnum = orderByEnum;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setLoadedItems(List<String> loadedItems) {
        this.loadedItems = loadedItems;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public static class Filter {

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

    }

    public static class ColorCode {

        private String color;
        private String hash;
        private String variant;

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

        public String getVariant() {
            return variant;
        }
    }
}
