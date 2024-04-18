package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderModel implements Serializable {

    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("Channel")
    @Expose
    private String Channel;

    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("Quantity")
    @Expose
    private int Quantity;
    @SerializedName("Price")
    @Expose
    private int Price;
    @SerializedName("Fee")
    @Expose
    private int Fee;
    @SerializedName("Amount")
    @Expose
    private int Amount;
    @SerializedName("RetRefNumber")
    @Expose
    private String RetRefNumber;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("ProductTypeID")
    @Expose
    private int ProductTypeID;
    @SerializedName("CreatedDate")
    @Expose
    private String CreatedDate;
    @SerializedName("PaidDate")
    @Expose
    private String PaidDate;
    @SerializedName("TerminalName")
    @Expose
    private String TerminalName;
    @SerializedName("PIDNumber")
    @Expose
    private String PIDNumber;

    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("ProductTypeName")
    @Expose
    private String ProductTypeName;
    @SerializedName("ProductID")
    @Expose
    private int ProductID;

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductTypeName() {
        return ProductTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        ProductTypeName = productTypeName;
    }

    public String getTerminalName() {
        return TerminalName;
    }

    public void setTerminalName(String terminalName) {
        TerminalName = terminalName;
    }

    public String getPIDNumber() {
        return PIDNumber;
    }

    public void setPIDNumber(String PIDNumber) {
        this.PIDNumber = PIDNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getProductTypeID() {
        return ProductTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        ProductTypeID = productTypeID;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getFee() {
        return Fee;
    }

    public void setFee(int fee) {
        Fee = fee;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getRetRefNumber() {
        return RetRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        RetRefNumber = retRefNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}
