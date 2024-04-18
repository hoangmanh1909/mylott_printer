package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.SimpleResult;

@SuppressLint("ParcelCreator")
public class LoginResponse extends SimpleResult {
    @SerializedName("Value")
    private EmployeeModel employeeModel;

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

}
