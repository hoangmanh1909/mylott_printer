package com.mbl.lottery.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XSKTBaseInfoResponse {
    @SerializedName("WarehouseID")
    @Expose
    private Integer warehouseID;
    @SerializedName("WarehouseName")
    @Expose
    private String warehouseName;
    @SerializedName("RadioID")
    @Expose
    private Integer radioID;
    @SerializedName("RadioName")
    @Expose
    private String radioName;
    @SerializedName("ProviderID")
    @Expose
    private Integer providerID;
    @SerializedName("ProviderName")
    @Expose
    private String providerName;
    @SerializedName("LockerID")
    @Expose
    private Integer lockerID;
    @SerializedName("LockerCode")
    @Expose
    private String lockerCode;
    @SerializedName("DrawlerID")
    @Expose
    private Integer drawlerID;
    @SerializedName("DrawlerIndex")
    @Expose
    private String drawlerIndex;

    public Integer getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Integer warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getRadioID() {
        return radioID;
    }

    public void setRadioID(Integer radioID) {
        this.radioID = radioID;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public Integer getProviderID() {
        return providerID;
    }

    public void setProviderID(Integer providerID) {
        this.providerID = providerID;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getLockerID() {
        return lockerID;
    }

    public void setLockerID(Integer lockerID) {
        this.lockerID = lockerID;
    }

    public String getLockerCode() {
        return lockerCode;
    }

    public void setLockerCode(String lockerCode) {
        this.lockerCode = lockerCode;
    }

    public Integer getDrawlerID() {
        return drawlerID;
    }

    public void setDrawlerID(Integer drawlerID) {
        this.drawlerID = drawlerID;
    }

    public String getDrawlerIndex() {
        return drawlerIndex;
    }

    public void setDrawlerIndex(String drawlerIndex) {
        this.drawlerIndex = drawlerIndex;
    }
}
