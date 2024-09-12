package com.mbl.lottery.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XSKTBaseV1Request {
    @SerializedName("WarehouseID")
    @Expose
    private Integer warehouseID;
    @SerializedName("LockerID")
    @Expose
    private Integer lockerID;
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("IsLock")
    @Expose
    private String isLock;
    @SerializedName("CreatedByID")
    @Expose
    private Integer createdByID;
    @SerializedName("Status")
    @Expose
    private String status;

    public Integer getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Integer warehouseID) {
        this.warehouseID = warehouseID;
    }

    public Integer getLockerID() {
        return lockerID;
    }

    public void setLockerID(Integer lockerID) {
        this.lockerID = lockerID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public Integer getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(Integer createdByID) {
        this.createdByID = createdByID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
