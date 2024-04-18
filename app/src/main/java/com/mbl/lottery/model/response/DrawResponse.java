package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.SimpleResult;

import java.util.List;

@SuppressLint("ParcelCreator")
public class DrawResponse extends SimpleResult {

    @SerializedName("ListValue")
    private List<DrawModel> drawModels;

    public List<DrawModel> getDrawModels() {
        return drawModels;
    }
}
