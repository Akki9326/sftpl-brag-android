package com.ragtagger.brag.data.model.datas;

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
 * Created by nikhil.vadoliya on 03-11-2017.
 */


public class DataUser implements Parcelable {

    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private Long approvalStatus;
    private Long createdDate;
    private String lastModifiedBy;
    private Long lastModifiedDate;
    private Boolean isActive;
    private int userType;
    private Boolean isDeleted;
    private List<DataUserAddress> addresses;
    private String gstin;
    private DataState state;
    private DataChannel channelModel;
    private DataSaleType saleTypeModel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName == null ? "" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Long approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getUserType() {
        return userType;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<DataUserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DataUserAddress> addresses) {
        this.addresses = addresses;
    }

    public String getFullAddressWithNewLine() {
        if (getAddresses() != null && !getAddresses().isEmpty()) {
            DataUserAddress address = getAddresses().get(0);
            String landmark;
            if (address.getLandmark().isEmpty()) {
                landmark = address.getLandmark();
            } else {
                landmark = address.getLandmark() + " , ";
            }

            return address.getAddress() + " , " + landmark + address.getCity() + "\n"
                    + address.getState().getText() + " - " + address.getPincode();
        } else {
            return "";
        }

    }

    public String getFullAddress() {
        if (getAddresses() != null && !getAddresses().isEmpty()) {
            DataUserAddress address = getAddresses().get(0);

            String landmark;
            if (address.getLandmark().isEmpty()) {
                landmark = address.getLandmark();
            } else {
                landmark = address.getLandmark() + " , ";
            }
            return address.getAddress() + " , "
                    + landmark + address.getCity() + " , " + address.getState().getText() + " - "
                    + address.getPincode();
        } else {
            return "";
        }

    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public DataState getState() {
        return state;
    }

    public void setState(DataState state) {
        this.state = state;
    }

    public DataChannel getChannelModel() {
        return channelModel;
    }

    public void setChannelModel(DataChannel channelModel) {
        this.channelModel = channelModel;
    }

    public DataSaleType getSaleTypeModel() {
        return saleTypeModel;
    }

    public void setSaleTypeModel(DataSaleType saleTypeModel) {
        this.saleTypeModel = saleTypeModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeValue(this.approvalStatus);
        dest.writeValue(this.createdDate);
        dest.writeString(this.lastModifiedBy);
        dest.writeValue(this.lastModifiedDate);
        dest.writeValue(this.isActive);
        dest.writeInt(this.userType);
        dest.writeValue(this.isDeleted);
        dest.writeTypedList(this.addresses);
        dest.writeString(this.gstin);
        dest.writeParcelable(this.state, flags);
        dest.writeParcelable(this.channelModel, flags);
        dest.writeParcelable(this.saleTypeModel, flags);
    }

    public DataUser() {
    }

    protected DataUser(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.mobileNumber = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.approvalStatus = (Long) in.readValue(Long.class.getClassLoader());
        this.createdDate = (Long) in.readValue(Long.class.getClassLoader());
        this.lastModifiedBy = in.readString();
        this.lastModifiedDate = (Long) in.readValue(Long.class.getClassLoader());
        this.isActive = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.userType = in.readInt();
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.addresses = in.createTypedArrayList(DataUserAddress.CREATOR);
        this.gstin = in.readString();
        this.state = in.readParcelable(DataState.class.getClassLoader());
        this.channelModel = in.readParcelable(DataChannel.class.getClassLoader());
        this.saleTypeModel = in.readParcelable(DataSaleType.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataUser> CREATOR = new Parcelable.Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel source) {
            return new DataUser(source);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };
}
