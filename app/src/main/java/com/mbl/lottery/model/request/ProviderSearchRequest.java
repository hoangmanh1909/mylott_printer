package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderSearchRequest {

    @SerializedName("Code")
    @Expose
    private Object code;
    @SerializedName("MobilePhone")
    @Expose
    private Object mobilePhone;
    @SerializedName("Area")
    @Expose
    private Integer area;
    @SerializedName("IsLock")
    @Expose
    private Object isLock;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Object mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Object getIsLock() {
        return isLock;
    }

    public void setIsLock(Object isLock) {
        this.isLock = isLock;
    }
}
