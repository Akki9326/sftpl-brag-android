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
    private Boolean isDeleted;
    private List<DataUserAddress> addresses;

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
            return address.getAddress() + " , " + address.getLandmark() + " , "
                    + address.getCity() + "\n"
                    + address.getState().getText() + "-" + address.getPincode();
        } else {
            return "";
        }

    }

    public String getFullAddress() {
        if (getAddresses() != null && !getAddresses().isEmpty()) {
            DataUserAddress address = getAddresses().get(0);
            return address.getAddress() + " , " + address.getLandmark() + " , "
                    + address.getCity() + " , " + address.getState().getText() + " - "
                    + address.getPincode();
        } else {
            return "";
        }

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
        dest.writeValue(this.isDeleted);
        dest.writeTypedList(this.addresses);
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
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.addresses = in.createTypedArrayList(DataUserAddress.CREATOR);
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
