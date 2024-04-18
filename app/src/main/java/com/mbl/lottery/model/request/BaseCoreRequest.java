package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseCoreRequest {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private Object code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }
}
