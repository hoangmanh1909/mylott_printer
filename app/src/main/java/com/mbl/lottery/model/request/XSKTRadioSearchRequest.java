package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XSKTRadioSearchRequest {
    @SerializedName("Area")
    @Expose
    private Integer area;
    @SerializedName("Day")
    @Expose
    private Integer day;
    @SerializedName("IsDay")
    @Expose
    private String isDay;
    @SerializedName("DrawDate")
    @Expose
    private String drawDate;
    @SerializedName("ID")
    @Expose
    private Integer id;

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getIsDay() {
        return isDay;
    }

    public void setIsDay(String isDay) {
        this.isDay = isDay;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
