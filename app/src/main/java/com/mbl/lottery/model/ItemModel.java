package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemModel {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("OrderCode")
    @Expose
    private String orderCode;
    @SerializedName("ProductID")
    @Expose
    private Integer productID;
    @SerializedName("ProductTypeID")
    @Expose
    private Integer productTypeID;
    @SerializedName("Bag")
    @Expose
    private Integer bag;
    @SerializedName("DrawCode")
    @Expose
    private String drawCode;
    @SerializedName("DrawDate")
    @Expose
    private String drawDate;
    @SerializedName("LineA")
    @Expose
    private String lineA;
    @SerializedName("LineB")
    @Expose
    private String lineB;
    @SerializedName("LineC")
    @Expose
    private String lineC;
    @SerializedName("LineD")
    @Expose
    private String lineD;
    @SerializedName("LineE")
    @Expose
    private String lineE;
    @SerializedName("LineF")
    @Expose
    private String lineF;
    @SerializedName("SystemA")
    @Expose
    private Integer systemA;
    @SerializedName("SystemB")
    @Expose
    private Integer systemB;
    @SerializedName("SystemC")
    @Expose
    private Integer systemC;
    @SerializedName("SystemD")
    @Expose
    private Integer systemD;
    @SerializedName("SystemE")
    @Expose
    private Integer systemE;
    @SerializedName("SystemF")
    @Expose
    private Integer systemF;
    @SerializedName("PriceA")
    @Expose
    private Integer priceA;
    @SerializedName("PriceB")
    @Expose
    private Integer priceB;
    @SerializedName("PriceC")
    @Expose
    private Integer priceC;
    @SerializedName("PriceD")
    @Expose
    private Integer priceD;
    @SerializedName("PriceE")
    @Expose
    private Integer priceE;
    @SerializedName("PriceF")
    @Expose
    private Integer priceF;
    @SerializedName("Price")
    @Expose
    private Integer price;
    @SerializedName("ResultID")
    @Expose
    private Integer resultID;
    @SerializedName("IsResult")
    @Expose
    private String isResult;
    @SerializedName("IsWin")
    @Expose
    private String isWin;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ImgBefore")
    @Expose
    private String imgBefore;
    @SerializedName("ImgAfter")
    @Expose
    private String imgAfter;
    @SerializedName("ItemResult")
    @Expose
    private String itemResult;
    @SerializedName("Result")
    @Expose
    private String result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public Integer getBag() {
        return bag;
    }

    public void setBag(Integer bag) {
        this.bag = bag;
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

    public String getLineA() {
        return lineA;
    }

    public void setLineA(String lineA) {
        this.lineA = lineA;
    }

    public String getLineB() {
        return lineB;
    }

    public void setLineB(String lineB) {
        this.lineB = lineB;
    }

    public String getLineC() {
        return lineC;
    }

    public void setLineC(String lineC) {
        this.lineC = lineC;
    }

    public String getLineD() {
        return lineD;
    }

    public void setLineD(String lineD) {
        this.lineD = lineD;
    }

    public String getLineE() {
        return lineE;
    }

    public void setLineE(String lineE) {
        this.lineE = lineE;
    }

    public String getLineF() {
        return lineF;
    }

    public void setLineF(String lineF) {
        this.lineF = lineF;
    }

    public Integer getSystemA() {
        return systemA;
    }

    public void setSystemA(Integer systemA) {
        this.systemA = systemA;
    }

    public Integer getSystemB() {
        return systemB;
    }

    public void setSystemB(Integer systemB) {
        this.systemB = systemB;
    }

    public Integer getSystemC() {
        return systemC;
    }

    public void setSystemC(Integer systemC) {
        this.systemC = systemC;
    }

    public Integer getSystemD() {
        return systemD;
    }

    public void setSystemD(Integer systemD) {
        this.systemD = systemD;
    }

    public Integer getSystemE() {
        return systemE;
    }

    public void setSystemE(Integer systemE) {
        this.systemE = systemE;
    }

    public Integer getSystemF() {
        return systemF;
    }

    public void setSystemF(Integer systemF) {
        this.systemF = systemF;
    }

    public Integer getPriceA() {
        return priceA;
    }

    public void setPriceA(Integer priceA) {
        this.priceA = priceA;
    }

    public Integer getPriceB() {
        return priceB;
    }

    public void setPriceB(Integer priceB) {
        this.priceB = priceB;
    }

    public Integer getPriceC() {
        return priceC;
    }

    public void setPriceC(Integer priceC) {
        this.priceC = priceC;
    }

    public Integer getPriceD() {
        return priceD;
    }

    public void setPriceD(Integer priceD) {
        this.priceD = priceD;
    }

    public Integer getPriceE() {
        return priceE;
    }

    public void setPriceE(Integer priceE) {
        this.priceE = priceE;
    }

    public Integer getPriceF() {
        return priceF;
    }

    public void setPriceF(Integer priceF) {
        this.priceF = priceF;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getResultID() {
        return resultID;
    }

    public void setResultID(Integer resultID) {
        this.resultID = resultID;
    }

    public String getIsResult() {
        return isResult;
    }

    public void setIsResult(String isResult) {
        this.isResult = isResult;
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
