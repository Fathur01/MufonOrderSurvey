package com.muf.mymuf.mobilesurvey.list;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 16003041 on 17/02/2017.
 */

public class SearchResultList implements Parcelable {

    private String labelItem1;
    private String item1;
    private String labelItem2;
    private String item2;
    private String labelItem3;
    private String item3;
    private String labelItem4;
    private String item4;
    private String labelItem5;
    private String item5;
    private String labelItem6;
    private String item6;


    public SearchResultList() {
    }

    public SearchResultList(String labelItem1, String item1, String labelItem2, String item2,
                            String labelItem3, String item3, String labelItem4, String item4,
                            String labelItem5, String item5, String labelItem6, String item6) {

        this.labelItem1 = labelItem1;
        this.item1 = item1;
        this.labelItem2 = labelItem2;
        this.item2 = item2;
        this.labelItem3 = labelItem3;
        this.item3 = item3;
        this.labelItem4 = labelItem4;
        this.item4 = item4;
        this.labelItem5 = labelItem5;
        this.item5 = item5;
        this.labelItem6 = labelItem6;
        this.item6 = item6;
    }

    protected SearchResultList(Parcel in) {
        labelItem1 = in.readString();
        item1 = in.readString();
        labelItem2 = in.readString();
        item2 = in.readString();
        labelItem3 = in.readString();
        item3 = in.readString();
        labelItem4 = in.readString();
        item4 = in.readString();
        labelItem5 = in.readString();
        item5 = in.readString();
        labelItem6 = in.readString();
        item6 = in.readString();
    }

    public static final Creator<SearchResultList> CREATOR = new Creator<SearchResultList>() {
        @Override
        public SearchResultList createFromParcel(Parcel in) {
            return new SearchResultList(in);
        }

        @Override
        public SearchResultList[] newArray(int size) {
            return new SearchResultList[size];
        }
    };

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getLabelItem1() {
        return labelItem1;
    }

    public void setLabelItem1(String labelItem1) {
        this.labelItem1 = labelItem1;
    }

    public String getLabelItem2() {
        return labelItem2;
    }

    public void setLabelItem2(String labelItem2) {
        this.labelItem2 = labelItem2;
    }

    public String getLabelItem3() {
        return labelItem3;
    }

    public void setLabelItem3(String labelItem3) {
        this.labelItem3 = labelItem3;
    }

    public String getLabelItem4() {
        return labelItem4;
    }

    public void setLabelItem4(String labelItem4) {
        this.labelItem4 = labelItem4;
    }

    public String getLabelItem5() {
        return labelItem5;
    }

    public void setLabelItem5(String labelItem5) {
        this.labelItem5 = labelItem5;
    }

    public String getLabelItem6() {
        return labelItem6;
    }

    public void setLabelItem6(String labelItem6) {
        this.labelItem6 = labelItem6;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(labelItem1);
        parcel.writeString(item1);
        parcel.writeString(labelItem2);
        parcel.writeString(item2);
        parcel.writeString(labelItem3);
        parcel.writeString(item3);
        parcel.writeString(labelItem4);
        parcel.writeString(item4);
        parcel.writeString(labelItem5);
        parcel.writeString(item5);
        parcel.writeString(labelItem6);
        parcel.writeString(item6);
    }
}
