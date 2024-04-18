package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.ParamsModel;
import com.mbl.lottery.model.SimpleResult;

import java.util.List;

@SuppressLint("ParcelCreator")
public class ParamsResponse  extends SimpleResult {

    @SerializedName("ListValue")
    private List<ParamsModel> paramsModels;

    public List<ParamsModel> getParamsModels() {
        return paramsModels;
    }
}
