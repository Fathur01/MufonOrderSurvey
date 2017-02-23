package com.muf.mymuf.mobilesurvey.list;

/**
 * Created by 16003041 on 03/02/2017.
 */

public class CustDtlList {

    private String branch;
    private String contractNo;
    private String appNo;
    private String appStatus;

    public CustDtlList() {
    }

    public CustDtlList(String branch, String contractNo, String appNo, String appStatus) {
        this.branch = branch;
        this.contractNo = contractNo;
        this.appNo = appNo;
        this.appStatus = appStatus;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}
