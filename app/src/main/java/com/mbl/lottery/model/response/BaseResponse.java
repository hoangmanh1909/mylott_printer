package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.SimpleResult;


@SuppressLint("ParcelCreator")
public class BaseResponse extends SimpleResult {
    @SerializedName("Value")
    private Object value;

    public Object getValue() {
        return value;
    }
}

