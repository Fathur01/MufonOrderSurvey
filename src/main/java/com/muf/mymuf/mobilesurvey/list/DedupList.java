package com.muf.mymuf.mobilesurvey.list;

/**
 * Created by 16003041 on 03/02/2017.
 */

public class DedupList {

    private String customerId;
    private String customerOid;
    private String tipeCustomer;
    private String namaLengkap;
    private String noIdentitas;
    private String alamatDomisili;
    private String status;
    private String rowID;
    private Integer statusCode;
    private Integer insertedId;


    public DedupList() {
    }

    public DedupList(String customerId, String customerOid, String tipeCustomer, String namaLengkap,
                     String noIdentitas, String alamatDomisili, String status, String rowID,
                     Integer statusCode, Integer insertedId) {

        this.customerId = customerId;
        this.customerOid = customerOid;
        this.tipeCustomer = tipeCustomer;
        this.namaLengkap = namaLengkap;
        this.noIdentitas = noIdentitas;
        this.alamatDomisili = alamatDomisili;
        this.status = status;
        this.rowID = rowID;
        this.statusCode = statusCode;
        this.insertedId = insertedId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerOid() {
        return customerOid;
    }

    public void setCustomerOid(String customerOid) {
        this.customerOid = customerOid;
    }

    public String getTipeCustomer() {
        return tipeCustomer;
    }

    public void setTipeCustomer(String tipeCustomer) {
        this.tipeCustomer = tipeCustomer;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoIdentitas() {
        return noIdentitas;
    }

    public void setNoIdentitas(String noIdentitas) {
        this.noIdentitas = noIdentitas;
    }

    public String getAlamatDomisili() {
        return alamatDomisili;
    }

    public void setAlamatDomisili(String alamatDomisili) {
        this.alamatDomisili = alamatDomisili;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getInsertedId() {
        return insertedId;
    }

    public void setInsertedId(Integer insertedId) {
        this.insertedId = insertedId;
    }
}
