package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;


@SuppressLint("ParcelCreator")
public class PrintResponse extends SimpleResult {
    @SerializedName("Value")
    private PrintCommandModel printCommandModel;

    public PrintCommandModel getPrintCommandModel() {
        return printCommandModel;
    }

    public void setPrintCommandModel(PrintCommandModel printCommandModel) {
        this.printCommandModel = printCommandModel;
    }
}
