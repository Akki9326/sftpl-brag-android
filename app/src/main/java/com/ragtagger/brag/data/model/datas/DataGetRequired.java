package com.ragtagger.brag.data.model.datas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataGetRequired implements Parcelable {

    @SerializedName("states")
    @Expose
    private List<DataState> states = null;
    @SerializedName("channels")
    @Expose
    private List<DataChannel> channels = null;
    @SerializedName("salesTypes")
    @Expose
    private List<DataSaleType> salesTypes = null;

    public List<DataState> getStates() {
        return states;
    }

    public List<DataChannel> getChannels() {
        return channels;
    }

    public List<DataSaleType> getSalesTypes() {
        return salesTypes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.states);
        dest.writeTypedList(this.channels);
        dest.writeTypedList(this.salesTypes);
    }

    public DataGetRequired() {
    }

    protected DataGetRequired(Parcel in) {
        this.states = in.createTypedArrayList(DataState.CREATOR);
        this.channels = in.createTypedArrayList(DataChannel.CREATOR);
        this.salesTypes = in.createTypedArrayList(DataSaleType.CREATOR);
    }

    public static final Parcelable.Creator<DataGetRequired> CREATOR = new Parcelable.Creator<DataGetRequired>() {
        @Override
        public DataGetRequired createFromParcel(Parcel source) {
            return new DataGetRequired(source);
        }

        @Override
        public DataGetRequired[] newArray(int size) {
            return new DataGetRequired[size];
        }
    };
}
