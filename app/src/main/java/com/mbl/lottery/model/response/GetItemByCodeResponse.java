package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.SimpleResult;

import java.util.List;


@SuppressLint("ParcelCreator")
public class GetItemByCodeResponse extends SimpleResult {

    @SerializedName("Value")
    private String printCode;
    @SerializedName("ListValue")
    private List<ItemModel> itemModels;

    public List<ItemModel> getItemModels() {
        return itemModels;
    }

    public String getPrintCode() {
        return printCode;
    }
}
