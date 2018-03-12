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

import java.util.List;

/**
 * Created by nikhil.vadoliya on 09-03-2018.
 */


public class CartItemData implements Parcelable {

    private String no;
    private String brandCode;
    private String colorCode;
    private String colorFamily;
    private String description;
    private String description2;
    private String gstCredit;
    private String hsnsacCode;
    private String itemCategoryCode;
    private String noOfComponent;
    private String occasion;
    private String petStyleCode;
    private String productGroupCode;
    private String seasonCode;
    private String sizeCode;
    private String unitOfMeasure;
    private Integer unitPrice;
    private long createdDate;
    private Integer stockData;
    private List<String> images = null;
    private Boolean alreadyInCart;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorFamily() {
        return colorFamily;
    }

    public void setColorFamily(String colorFamily) {
        this.colorFamily = colorFamily;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getGstCredit() {
        return gstCredit;
    }

    public void setGstCredit(String gstCredit) {
        this.gstCredit = gstCredit;
    }

    public String getHsnsacCode() {
        return hsnsacCode;
    }

    public void setHsnsacCode(String hsnsacCode) {
        this.hsnsacCode = hsnsacCode;
    }

    public String getItemCategoryCode() {
        return itemCategoryCode;
    }

    public void setItemCategoryCode(String itemCategoryCode) {
        this.itemCategoryCode = itemCategoryCode;
    }

    public String getNoOfComponent() {
        return noOfComponent;
    }

    public void setNoOfComponent(String noOfComponent) {
        this.noOfComponent = noOfComponent;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getPetStyleCode() {
        return petStyleCode;
    }

    public void setPetStyleCode(String petStyleCode) {
        this.petStyleCode = petStyleCode;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStockData() {
        return stockData;
    }

    public void setStockData(Integer stockData) {
        this.stockData = stockData;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getAlreadyInCart() {
        return alreadyInCart;
    }

    public void setAlreadyInCart(Boolean alreadyInCart) {
        this.alreadyInCart = alreadyInCart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.no);
        dest.writeString(this.brandCode);
        dest.writeString(this.colorCode);
        dest.writeString(this.colorFamily);
        dest.writeString(this.description);
        dest.writeString(this.description2);
        dest.writeString(this.gstCredit);
        dest.writeString(this.hsnsacCode);
        dest.writeString(this.itemCategoryCode);
        dest.writeString(this.noOfComponent);
        dest.writeString(this.occasion);
        dest.writeString(this.petStyleCode);
        dest.writeString(this.productGroupCode);
        dest.writeString(this.seasonCode);
        dest.writeString(this.sizeCode);
        dest.writeString(this.unitOfMeasure);
        dest.writeValue(this.unitPrice);
        dest.writeLong(this.createdDate);
        dest.writeValue(this.stockData);
        dest.writeStringList(this.images);
        dest.writeValue(this.alreadyInCart);
    }

    public CartItemData() {
    }

    protected CartItemData(Parcel in) {
        this.no = in.readString();
        this.brandCode = in.readString();
        this.colorCode = in.readString();
        this.colorFamily = in.readString();
        this.description = in.readString();
        this.description2 = in.readString();
        this.gstCredit = in.readString();
        this.hsnsacCode = in.readString();
        this.itemCategoryCode = in.readString();
        this.noOfComponent = in.readString();
        this.occasion = in.readString();
        this.petStyleCode = in.readString();
        this.productGroupCode = in.readString();
        this.seasonCode = in.readString();
        this.sizeCode = in.readString();
        this.unitOfMeasure = in.readString();
        this.unitPrice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdDate = (Long) in.readValue(Long.class.getClassLoader());
        this.stockData = (Integer) in.readValue(Integer.class.getClassLoader());
        this.images = in.createStringArrayList();
        this.alreadyInCart = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CartItemData> CREATOR = new Parcelable.Creator<CartItemData>() {
        @Override
        public CartItemData createFromParcel(Parcel source) {
            return new CartItemData(source);
        }

        @Override
        public CartItemData[] newArray(int size) {
            return new CartItemData[size];
        }
    };
}
