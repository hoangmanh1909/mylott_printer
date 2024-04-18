package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.ImageModel;

import java.util.List;

public class OrderImagesRequest {
    @SerializedName("OrderCode")
    @Expose
    private String orderCode;
    @SerializedName("ItemID")
    @Expose
    private String itemID;
    @SerializedName("ImgBefore")
    @Expose
    private String imgBefore;
    @SerializedName("ImgAfter")
    @Expose
    private String imgAfter;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
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

}
