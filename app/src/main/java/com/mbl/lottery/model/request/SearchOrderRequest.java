package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchOrderRequest {
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("Status")
    @Expose
    private String Status = "S";
    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("FromDate")
    @Expose
    private String FromDate;
    @SerializedName("ToDate")
    @Expose
    private String ToDate;
    @SerializedName("ProductID")
    @Expose
    private int ProductID;
    @SerializedName("TerminalID")
    @Expose
    private int TerminalID;
    @SerializedName("Type")
    @Expose
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getTerminalID() {
        return TerminalID;
    }

    public void setTerminalID(int terminalID) {
        TerminalID = terminalID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

}
