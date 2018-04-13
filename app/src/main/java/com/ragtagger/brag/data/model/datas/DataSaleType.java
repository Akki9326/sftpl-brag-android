package com.ragtagger.brag.data.model.datas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSaleType implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text")
    @Expose
    private String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.text);
    }

    public DataSaleType() {
    }

    protected DataSaleType(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<DataSaleType> CREATOR = new Parcelable.Creator<DataSaleType>() {
        @Override
        public DataSaleType createFromParcel(Parcel source) {
            return new DataSaleType(source);
        }

        @Override
        public DataSaleType[] newArray(int size) {
            return new DataSaleType[size];
        }
    };
}
