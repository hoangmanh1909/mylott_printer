package com.mbl.lottery.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class XSKTTicketSymbol  implements Serializable {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Symbol")
    @Expose
    private Object symbol;
    @SerializedName("IsPaid")
    @Expose
    private Object isPaid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getSymbol() {
        return symbol;
    }

    public void setSymbol(Object symbol) {
        this.symbol = symbol;
    }

    public Object getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Object isPaid) {
        this.isPaid = isPaid;
    }
}