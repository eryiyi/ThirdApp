package com.example.thirdapp.module;

import java.io.Serializable;

/**
 "uid": "12",
 "sUserId": "17",
 "sAddress": "广东深圳福田",
 "sStreet": "福华一路",
 "sZip": "518000",
 "sAcceptName": "Marks",
 "sTelephone": "13266816551",
 "nSelected": "1",
 "nIsDel": "0",
 "nRegisterDate": "0",
 "nUpdateDate": "0"
 */
public class ShoppingAddress implements Serializable{
    private String uid;
    private String sUserId;
    private String sAddress;
    private String sStreet;
    private String sZip;
    private String sAcceptName;
    private String sTelephone;
    private String nSelected;
    private String nIsDel;
    private String nRegisterDate;
    private String nUpdateDate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getsUserId() {
        return sUserId;
    }

    public void setsUserId(String sUserId) {
        this.sUserId = sUserId;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsStreet() {
        return sStreet;
    }

    public void setsStreet(String sStreet) {
        this.sStreet = sStreet;
    }

    public String getsZip() {
        return sZip;
    }

    public void setsZip(String sZip) {
        this.sZip = sZip;
    }

    public String getsAcceptName() {
        return sAcceptName;
    }

    public void setsAcceptName(String sAcceptName) {
        this.sAcceptName = sAcceptName;
    }

    public String getsTelephone() {
        return sTelephone;
    }

    public void setsTelephone(String sTelephone) {
        this.sTelephone = sTelephone;
    }

    public String getnSelected() {
        return nSelected;
    }

    public void setnSelected(String nSelected) {
        this.nSelected = nSelected;
    }

    public String getnIsDel() {
        return nIsDel;
    }

    public void setnIsDel(String nIsDel) {
        this.nIsDel = nIsDel;
    }

    public String getnRegisterDate() {
        return nRegisterDate;
    }

    public void setnRegisterDate(String nRegisterDate) {
        this.nRegisterDate = nRegisterDate;
    }

    public String getnUpdateDate() {
        return nUpdateDate;
    }

    public void setnUpdateDate(String nUpdateDate) {
        this.nUpdateDate = nUpdateDate;
    }
}
