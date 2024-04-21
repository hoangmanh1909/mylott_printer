package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrawModel implements Serializable {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("DrawCode")
    @Expose
    private String drawCode;
    @SerializedName("DrawDate")
    @Expose
    private String drawDate;
    @SerializedName("DrawTime")
    @Expose
    private String drawTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrawCode() {
        return drawCode;
    }

    public void setDrawCode(String drawCode) {
        this.drawCode = drawCode;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }
}
