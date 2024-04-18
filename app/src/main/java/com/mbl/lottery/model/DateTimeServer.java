package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateTimeServer {
    @SerializedName("DateTimeNow")
    @Expose
    private String DateTimeNow;

    public String getDateTimeNow() {
        return DateTimeNow;
    }

    public void setDateTimeNow(String dateTimeNow) {
        DateTimeNow = dateTimeNow;
    }
}
