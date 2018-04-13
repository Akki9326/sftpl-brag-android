package com.ragtagger.brag.data.model.datas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataChannel implements Parcelable {

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

    public DataChannel() {
    }

    protected DataChannel(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<DataChannel> CREATOR = new Parcelable.Creator<DataChannel>() {
        @Override
        public DataChannel createFromParcel(Parcel source) {
            return new DataChannel(source);
        }

        @Override
        public DataChannel[] newArray(int size) {
            return new DataChannel[size];
        }
    };
}
