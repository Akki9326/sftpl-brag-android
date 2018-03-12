package com.pulse.brag.data.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
    private DataProductList.Filter filter;
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

    public void setFilter(DataProductList.Filter filter) {
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
}
