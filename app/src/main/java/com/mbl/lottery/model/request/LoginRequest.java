package com.mbl.lottery.model.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String mobileNumber) {
        this.userName = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
