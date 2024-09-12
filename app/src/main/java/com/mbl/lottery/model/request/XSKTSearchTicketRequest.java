package com.mbl.lottery.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XSKTSearchTicketRequest {
    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("FromDrawDate")
    @Expose
    private String fromDrawDate;
    @SerializedName("ToDrawDate")
    @Expose
    private String toDrawDate;
    @SerializedName("Status")
    @Expose
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFromDrawDate() {
        return fromDrawDate;
    }

    public void setFromDrawDate(String fromDrawDate) {
        this.fromDrawDate = fromDrawDate;
    }

    public String getToDrawDate() {
        return toDrawDate;
    }

    public void setToDrawDate(String toDrawDate) {
        this.toDrawDate = toDrawDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
