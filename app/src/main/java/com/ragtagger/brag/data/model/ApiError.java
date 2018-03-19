package com.ragtagger.brag.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alpesh.rathod on 2/6/2018.
 */

public class ApiError implements Parcelable {
    private int httpCode;
    private String message;
    public String status;
    public String type;

    public ApiError() {

    }

    public ApiError(int str, String str2) {
        this.httpCode = str;
        this.message = str2;
    }

    protected ApiError(Parcel parcel) {
        this.httpCode = parcel.readInt();
        this.message = parcel.readString();
        this.type = parcel.readString();
        this.status = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.httpCode);
        parcel.writeString(this.message);
        parcel.writeString(this.type);
        parcel.writeString(this.status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiError> CREATOR = new Creator<ApiError>() {
        @Override
        public ApiError createFromParcel(Parcel in) {
            return new ApiError(in);
        }

        @Override
        public ApiError[] newArray(int size) {
            return new ApiError[size];
        }
    };

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
