package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;

import java.util.List;

@SuppressLint("ParcelCreator")
public class SearchOrderResponse extends SimpleResult {

    @SerializedName("ListValue")
    private List<OrderModel> orderModels;

    public List<OrderModel> getOrderModels() {
        return orderModels;
    }
}
