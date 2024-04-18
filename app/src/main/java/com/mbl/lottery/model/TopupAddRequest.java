package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopupAddRequest {
    @SerializedName("EmpID")
    @Expose
    private int empID;
    @SerializedName("RefCode")
    @Expose
    private String refCode;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Amount")
    @Expose
    private int amount;
    @SerializedName("Message")
    @Expose
    private String message;

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
