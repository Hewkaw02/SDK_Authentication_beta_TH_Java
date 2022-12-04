package com.tni.phitchaya.sdk_authentication_th_java.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class POJO_IDcard {
    @SerializedName("Date_Birthday_en")
    @Expose
    private String dateBirthdayEn;
    @SerializedName("Date_Birthday_th")
    @Expose
    private List<String> dateBirthdayTh = null;
    @SerializedName("fullname_th")
    @Expose
    private String fullnameTh;
    @SerializedName("idnum")
    @Expose
    private String idnum;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("Staus")
    @Expose
    private String staus;

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getDateBirthdayEn() {
        return dateBirthdayEn;
    }

    public void setDateBirthdayEn(String dateBirthdayEn) {
        this.dateBirthdayEn = dateBirthdayEn;
    }

    public List<String> getDateBirthdayTh() {
        return dateBirthdayTh;
    }

    public void setDateBirthdayTh(List<String> dateBirthdayTh) {
        this.dateBirthdayTh = dateBirthdayTh;
    }

    public String getFullnameTh() {
        return fullnameTh;
    }

    public void setFullnameTh(String fullnameTh) {
        this.fullnameTh = fullnameTh;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

}