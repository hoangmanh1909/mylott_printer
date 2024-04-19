package com.mbl.lottery.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TogetherTicketSearchResponse {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("CreatedID")
    @Expose
    private Integer createdID;
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
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Percent")
    @Expose
    private Integer percent;
    @SerializedName("IsResult")
    @Expose
    private String isResult;
    @SerializedName("Payout")
    @Expose
    private Integer payout;
    @SerializedName("Tax")
    @Expose
    private Integer tax;
    @SerializedName("Prize")
    @Expose
    private Integer prize;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CancelledBy")
    @Expose
    private Integer cancelledBy;
    @SerializedName("CancelledDate")
    @Expose
    private String cancelledDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCreatedID() {
        return createdID;
    }

    public void setCreatedID(Integer createdID) {
        this.createdID = createdID;
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

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getIsResult() {
        return isResult;
    }

    public void setIsResult(String isResult) {
        this.isResult = isResult;
    }

    public Integer getPayout() {
        return payout;
    }

    public void setPayout(Integer payout) {
        this.payout = payout;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(Integer cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(String cancelledDate) {
        this.cancelledDate = cancelledDate;
    }
}
