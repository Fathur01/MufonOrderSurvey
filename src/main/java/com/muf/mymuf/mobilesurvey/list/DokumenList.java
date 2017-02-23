package com.muf.mymuf.mobilesurvey.list;

/**
 * Created by 16003041 on 16/02/2017.
 */

public class DokumenList {

    private String name;
    private Boolean isOther;

    public DokumenList() {
    }

    public DokumenList(String name, Boolean isOther) {
        this.name = name;
        this.isOther = isOther;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsOther() {
        return isOther;
    }

    public void setIsOther(Boolean isOther) {
        isOther = isOther;
    }
}
