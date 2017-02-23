package com.muf.mymuf.mobilesurvey.model;

/**
 * Created by 16003041 on 02/02/2017.
 */

public class MktOrder {

    Integer id;
    String orderID;
    String userID;
    String noKTP;
    String namaNasabah;
    String tglLahir;
    String namaIbu;
    Double latitude;
    Double longitude;
    String sentTime;

    public MktOrder(){

    }

    public MktOrder(Integer id, String orderID, String userID, String noKTP, String namaNasabah, String tglLahir,
                    String namaIbu, Double latitude, Double longitude, String sentTime){

        this.id = id;
        this.orderID = orderID;
        this.userID = userID;
        this.noKTP = noKTP;
        this.namaNasabah = namaNasabah;
        this.tglLahir = tglLahir;
        this.namaIbu = namaIbu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sentTime = sentTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getNamaNasabah() {
        return namaNasabah;
    }

    public void setNamaNasabah(String namaNasabah) {
        this.namaNasabah = namaNasabah;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }
}
