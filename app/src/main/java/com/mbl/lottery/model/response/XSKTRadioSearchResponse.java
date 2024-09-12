package com.mbl.lottery.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XSKTRadioSearchResponse {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Area")
    @Expose
    private Integer area;
    @SerializedName("IsLock")
    @Expose
    private String isLock;
    @SerializedName("Monday")
    @Expose
    private String monday;
    @SerializedName("Tuesday")
    @Expose
    private String tuesday;
    @SerializedName("Wednesday")
    @Expose
    private String wednesday;
    @SerializedName("Thursday")
    @Expose
    private String thursday;
    @SerializedName("Friday")
    @Expose
    private String friday;
    @SerializedName("Saturday")
    @Expose
    private String saturday;
    @SerializedName("Sunday")
    @Expose
    private String sunday;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CloseSaleTime")
    @Expose
    private String closeSaleTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCloseSaleTime() {
        return closeSaleTime;
    }

    public void setCloseSaleTime(String closeSaleTime) {
        this.closeSaleTime = closeSaleTime;
    }

}
