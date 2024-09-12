package com.mbl.lottery.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XSKTAddTicketRequest {
    @SerializedName("AmndID")
    @Expose
    private Integer amndID;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("Images")
    @Expose
    private String images;
    @SerializedName("DrawlerID")
    @Expose
    private Integer drawlerID;
    @SerializedName("ProviderID")
    @Expose
    private Integer providerID;
    @SerializedName("RadioID")
    @Expose
    private Integer radioID;
    @SerializedName("Symbol")
    @Expose
    private String symbol;
    @SerializedName("DrawDate")
    @Expose
    private String drawDate;

    public Integer getAmndID() {
        return amndID;
    }

    public void setAmndID(Integer amndID) {
        this.amndID = amndID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getDrawlerID() {
        return drawlerID;
    }

    public void setDrawlerID(Integer drawlerID) {
        this.drawlerID = drawlerID;
    }

    public Integer getProviderID() {
        return providerID;
    }

    public void setProviderID(Integer providerID) {
        this.providerID = providerID;
    }

    public Integer getRadioID() {
        return radioID;
    }

    public void setRadioID(Integer radioID) {
        this.radioID = radioID;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }
}
