package com.muf.mymuf.mobilesurvey.list;

/**
 * Created by 16003041 on 14/02/2017.
 */

public class TimeLineList {

    private String name;
    private Integer amount;

    public TimeLineList() {
    }

    public TimeLineList(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
