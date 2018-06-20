package com.ragtagger.brag.data.model.datas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DataSubCategory implements Parcelable {

    private String q;
    private List<DataObject> objects;
    private int count;

    DataSubCategory() {

    }

    protected DataSubCategory(Parcel in) {
        q = in.readString();
        objects = in.createTypedArrayList(DataObject.CREATOR);
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(q);
        dest.writeTypedList(objects);
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataSubCategory> CREATOR = new Creator<DataSubCategory>() {
        @Override
        public DataSubCategory createFromParcel(Parcel in) {
            return new DataSubCategory(in);
        }

        @Override
        public DataSubCategory[] newArray(int size) {
            return new DataSubCategory[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public List<DataObject> getObjects() {
        return objects;
    }

    public void setObjects(List<DataObject> objects) {
        this.objects = objects;
    }


    public static class DataObject implements Parcelable {
        private String id;
        private String optionName;
        private String url;
        private String sizeGuide;
        private String[] banners;

        public DataObject() {

        }

        protected DataObject(Parcel in) {
            id = in.readString();
            optionName = in.readString();
            url = in.readString();
            sizeGuide = in.readString();
            banners = in.createStringArray();
        }

        public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
            @Override
            public DataObject createFromParcel(Parcel in) {
                return new DataObject(in);
            }

            @Override
            public DataObject[] newArray(int size) {
                return new DataObject[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSizeGuide() {
            return sizeGuide;
        }

        public void setSizeGuide(String sizeGuide) {
            this.sizeGuide = sizeGuide;
        }

        public String[] getBanners() {
            return banners;
        }

        public void setBanners(String[] banners) {
            this.banners = banners;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(optionName);
            parcel.writeString(url);
            parcel.writeString(sizeGuide);
            parcel.writeStringArray(banners);
        }
    }
}
