package com.ragtagger.brag.data.model.datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alpesh.rathod on 3/9/2018.
 */

public class DataAddToCart {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("item")
    @Expose
    private CartItem item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public CartItem getItem() {
        return item;
    }

    public void setItem(CartItem item) {
        this.item = item;
    }

    public static class CartItem {
        @SerializedName("no")
        @Expose
        private String no;
        @SerializedName("brandCode")
        @Expose
        private String brandCode;
        @SerializedName("colorCode")
        @Expose
        private String colorCode;
        @SerializedName("colorFamily")
        @Expose
        private String colorFamily;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("description2")
        @Expose
        private String description2;
        @SerializedName("gstCredit")
        @Expose
        private String gstCredit;
        @SerializedName("hsnsacCode")
        @Expose
        private String hsnsacCode;
        @SerializedName("itemCategoryCode")
        @Expose
        private String itemCategoryCode;
        @SerializedName("noOfComponent")
        @Expose
        private String noOfComponent;
        @SerializedName("occasion")
        @Expose
        private String occasion;
        @SerializedName("petStyleCode")
        @Expose
        private String petStyleCode;
        @SerializedName("productGroupCode")
        @Expose
        private String productGroupCode;
        @SerializedName("seasonCode")
        @Expose
        private String seasonCode;
        @SerializedName("sizeCode")
        @Expose
        private String sizeCode;
        @SerializedName("unitOfMeasure")
        @Expose
        private String unitOfMeasure;
        @SerializedName("unitPrice")
        @Expose
        private int unitPrice;
        @SerializedName("createdDate")
        @Expose
        private long createdDate;
        @SerializedName("stockData")
        @Expose
        private int stockData;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("alreadyInCart")
        @Expose
        private boolean alreadyInCart;

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

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public int getStockData() {
            return stockData;
        }

        public void setStockData(int stockData) {
            this.stockData = stockData;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public boolean isAlreadyInCart() {
            return alreadyInCart;
        }

        public void setAlreadyInCart(boolean alreadyInCart) {
            this.alreadyInCart = alreadyInCart;
        }
    }
}
