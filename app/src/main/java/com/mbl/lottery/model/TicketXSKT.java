package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketXSKT {

    @SerializedName("Line")
    @Expose
    private String line;
    @SerializedName("System")
    @Expose
    private Integer system;
    @SerializedName("DrawlerIndex")
    @Expose
    private String drawlerIndex;

    @SerializedName("Locker")
    @Expose
    private String locker;

    @SerializedName("Symbol")
    @Expose
    private String symbol;



    public String getLine() {
        return line;
    }

    public void setLine(String lineA) {
        this.line = lineA;
    }


    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer systemA) {
        this.system = systemA;
    }

    public String getDrawlerIndex() {
        return drawlerIndex;
    }

    public void setDrawlerIndex(String drawlerIndexA) {
        this.drawlerIndex = drawlerIndexA;
    }


    public String getLocker() {
        return locker;
    }

    public void setLocker(String lockerA) {
        this.locker = lockerA;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbolA) {
        this.symbol = symbolA;
    }

}
