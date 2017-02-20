package com.muf.mymuf.mobilesurvey.list;

/**
 * Created by 16003041 on 06/02/2017.
 */

public class ToDoList {

    private String applicantOrderId;
    private String applicantNo;
    private String orderDate;
    private String customerName;
    private String process;

    public ToDoList() {
    }

    public ToDoList(String applicantOrderId, String applicantNo, String orderDate, String customerName,
                     String process) {

        this.applicantOrderId = applicantOrderId;
        this.applicantNo = applicantNo;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.process = process;
    }

    public String getApplicantOrderId() {
        return applicantOrderId;
    }

    public void setApplicantOrderId(String applicantOrderId) {
        this.applicantOrderId = applicantOrderId;
    }

    public String getApplicantNo() {
        return applicantNo;
    }

    public void setApplicantNo(String applicantNo) {
        this.applicantNo = applicantNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
