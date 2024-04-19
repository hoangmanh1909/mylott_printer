package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TogetherTicketSearchRequest {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ProductID")
    @Expose
    private Integer productID;
    @SerializedName("Systematic")
    @Expose
    private Integer systematic;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getSystematic() {
        return systematic;
    }

    public void setSystematic(Integer systematic) {
        this.systematic = systematic;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
