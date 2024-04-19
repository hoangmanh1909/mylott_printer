package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TogetherTicketEditRequest {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ProductID")
    @Expose
    private Integer productID;
    @SerializedName("Systematic")
    @Expose
    private Integer systematic;
    @SerializedName("DrawCode")
    @Expose
    private String drawCode;
    @SerializedName("DrawDate")
    @Expose
    private String drawDate;
    @SerializedName("NumberOfLines")
    @Expose
    private String numberOfLines;
    @SerializedName("ImgBefore")
    @Expose
    private String imgBefore;
    @SerializedName("ImgAfter")
    @Expose
    private String imgAfter;
    @SerializedName("Price")
    @Expose
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getSystematic() {
        return systematic;
    }

    public void setSystematic(Integer systematic) {
        this.systematic = systematic;
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

    public String getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(String numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public String getImgBefore() {
        return imgBefore;
    }

    public void setImgBefore(String imgBefore) {
        this.imgBefore = imgBefore;
    }

    public String getImgAfter() {
        return imgAfter;
    }

    public void setImgAfter(String imgAfter) {
        this.imgAfter = imgAfter;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
