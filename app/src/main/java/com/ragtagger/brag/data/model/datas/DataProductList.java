package com.ragtagger.brag.data.model.datas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpesh.rathod on 3/7/2018.
 */

public class DataProductList implements Parcelable {


    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("objects")
    @Expose
    private List<Products> objects = null;
    @SerializedName("filter")
    @Expose
    private DataFilter.Filter filter;
    @SerializedName("count")
    @Expose
    private int count;
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
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("alreadyInCart")
    @Expose
    private boolean alreadyInCart;

    public DataFilter.Filter getFilter() {
        return filter;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public List<Products> getObjects() {
        return objects;
    }

    public void setObjects(List<Products> objects) {
        this.objects = objects;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setFilter(DataFilter.Filter filter) {
        this.filter = filter;
    }

    public boolean isAlreadyInCart() {
        return alreadyInCart;
    }

    public void setAlreadyInCart(boolean alreadyInCart) {
        this.alreadyInCart = alreadyInCart;
    }

    public static class Products implements Parcelable {
        @SerializedName("no")
        @Expose
        private String no;
        @SerializedName("sizes")
        @Expose
        private List<Size> sizes = null;
        @SerializedName("itemCategoryCode")
        @Expose
        private String itemCategoryCode;
        @SerializedName("brandCode")
        @Expose
        private String brandCode;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("description2")
        @Expose
        private String description2;
        @SerializedName("productGroupCode")
        @Expose
        private String productGroupCode;
        @SerializedName("petStyleCode")
        @Expose
        private String petStyleCode;
        @SerializedName("colorCode")
        @Expose
        private String colorCode;
        @SerializedName("colorHexCode")
        @Expose
        private String colorHexCode;
        @SerializedName("colorFamily")
        @Expose
        private String colorFamily;
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
        private double unitPrice;
        @SerializedName("stockData")
        @Expose
        private int stockData;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("alreadyInCart")
        @Expose
        private boolean alreadyInCart;

        public String getColorHexCode() {
            return colorHexCode;
        }

        public void setColorHexCode(String colorHexCode) {
            this.colorHexCode = colorHexCode;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public List<Size> getSizes() {
            return sizes;
        }

        public void setSizes(List<Size> sizes) {
            this.sizes = sizes;
        }

        public String getItemCategoryCode() {
            return itemCategoryCode;
        }

        public void setItemCategoryCode(String itemCategoryCode) {
            this.itemCategoryCode = itemCategoryCode;
        }

        public String getBrandCode() {
            return brandCode;
        }

        public void setBrandCode(String brandCode) {
            this.brandCode = brandCode;
        }

        public String getDescription2() {
            return description2;
        }

        public void setDescription2(String description2) {
            this.description2 = description2;
        }

        public String getProductGroupCode() {
            return productGroupCode;
        }

        public void setProductGroupCode(String productGroupCode) {
            this.productGroupCode = productGroupCode;
        }

        public String getPetStyleCode() {
            return petStyleCode;
        }

        public void setPetStyleCode(String petStyleCode) {
            this.petStyleCode = petStyleCode;
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

        public String getSeasonCode() {
            return seasonCode;
        }

        public void setSeasonCode(String seasonCode) {
            this.seasonCode = seasonCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.no);
            dest.writeList(this.sizes);
            dest.writeString(this.itemCategoryCode);
            dest.writeString(this.brandCode);
            dest.writeString(this.description);
            dest.writeString(this.description2);
            dest.writeString(this.productGroupCode);
            dest.writeString(this.petStyleCode);
            dest.writeString(this.colorCode);
            dest.writeString(this.colorFamily);
            dest.writeString(this.seasonCode);
            dest.writeString(this.sizeCode);
            dest.writeString(this.unitOfMeasure);
            dest.writeDouble(this.unitPrice);
            dest.writeInt(this.stockData);
            dest.writeStringList(this.images);
            dest.writeByte(this.alreadyInCart ? (byte) 1 : (byte) 0);
        }

        public Products() {
        }

        protected Products(Parcel in) {
            this.no = in.readString();
            this.sizes = new ArrayList<Size>();
            in.readList(this.sizes, Size.class.getClassLoader());
            this.itemCategoryCode = in.readString();
            this.brandCode = in.readString();
            this.description = in.readString();
            this.description2 = in.readString();
            this.productGroupCode = in.readString();
            this.petStyleCode = in.readString();
            this.colorCode = in.readString();
            this.colorFamily = in.readString();
            this.seasonCode = in.readString();
            this.sizeCode = in.readString();
            this.unitOfMeasure = in.readString();
            this.unitPrice = in.readDouble();
            this.stockData = in.readInt();
            this.images = in.createStringArrayList();
            this.alreadyInCart = in.readByte() != 0;
        }

        public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
            @Override
            public Products createFromParcel(Parcel source) {
                return new Products(source);
            }

            @Override
            public Products[] newArray(int size) {
                return new Products[size];
            }
        };
    }

    public static class Size implements Parcelable {

        @SerializedName("no")
        @Expose
        private String no;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("description2")
        @Expose
        private String description2;
        @SerializedName("sizeCode")
        @Expose
        private String sizeCode;
        @SerializedName("unitOfMeasure")
        @Expose
        private String unitOfMeasure;
        @SerializedName("unitPrice")
        @Expose
        private int unitPrice;
        @SerializedName("stockData")
        @Expose
        private int stockData;
        @SerializedName("isDefault")
        @Expose
        private boolean isDefault = false;
        @SerializedName("images")
        @Expose
        private List<String> images = null;

        public Size(String no, String description, String description2, String sizeCode,
                    String unitOfMeasure, double unitPrice, int stockData, boolean isDefault, List<String> images) {
            this.no = no;
            this.description = description;
            this.description2 = description2;
            this.sizeCode = sizeCode;
            this.unitOfMeasure = unitOfMeasure;
            this.unitPrice = (int) unitPrice;
            this.stockData = stockData;
            this.isDefault = isDefault;
            this.images = images;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
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

        public String getDescription2Modified() {
            if (description2 == null)
                return "";
            else
                return description2.replace("|", "\n\u2022");
        }

        public void setDescription2(String description2) {
            this.description2 = description2;
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

        public int getStockData() {
            return stockData;
        }

        public void setStockData(int stockData) {
            this.stockData = stockData;
        }

        public boolean isIsDefault() {
            return isDefault;
        }

        public void setIsDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.no);
            dest.writeString(this.description);
            dest.writeString(this.description2);
            dest.writeString(this.sizeCode);
            dest.writeString(this.unitOfMeasure);
            dest.writeInt(this.unitPrice);
            dest.writeInt(this.stockData);
            dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
            dest.writeStringList(this.images);
        }

        public Size() {
        }

        protected Size(Parcel in) {
            this.no = in.readString();
            this.description = in.readString();
            this.description2 = in.readString();
            this.sizeCode = in.readString();
            this.unitOfMeasure = in.readString();
            this.unitPrice = in.readInt();
            this.stockData = in.readInt();
            this.isDefault = in.readByte() != 0;
            this.images = in.createStringArrayList();
        }

        public static final Creator<Size> CREATOR = new Creator<Size>() {
            @Override
            public Size createFromParcel(Parcel source) {
                return new Size(source);
            }

            @Override
            public Size[] newArray(int size) {
                return new Size[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.q);
        dest.writeTypedList(this.objects);
        dest.writeParcelable(this.filter, flags);
        dest.writeInt(this.count);
        dest.writeStringList(this.loadedItems);
        dest.writeString(this.category);
        dest.writeString(this.subCategory);
        dest.writeString(this.seasonCode);
        dest.writeString(this.orderBy);
        dest.writeByte(this.alreadyInCart ? (byte) 1 : (byte) 0);
    }

    public DataProductList() {
    }

    protected DataProductList(Parcel in) {
        this.q = in.readString();
        this.objects = in.createTypedArrayList(Products.CREATOR);
        this.filter = in.readParcelable(DataFilter.Filter.class.getClassLoader());
        this.count = in.readInt();
        this.loadedItems = in.createStringArrayList();
        this.category = in.readString();
        this.subCategory = in.readString();
        this.seasonCode = in.readString();
        this.orderBy = in.readString();
        this.alreadyInCart = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DataProductList> CREATOR = new Parcelable.Creator<DataProductList>() {
        @Override
        public DataProductList createFromParcel(Parcel source) {
            return new DataProductList(source);
        }

        @Override
        public DataProductList[] newArray(int size) {
            return new DataProductList[size];
        }
    };
}
