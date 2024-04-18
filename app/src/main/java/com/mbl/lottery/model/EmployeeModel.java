package com.mbl.lottery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeModel{
    @SerializedName("ID")
    public int iD;
    @SerializedName("UserName")
    public String userName;
    @SerializedName("FullName")
    public String fullName;
    @SerializedName("MobileNumber")
    public String mobileNumber;
    @SerializedName("EmailAddress")
    public String emailAddress;
    @SerializedName("GroupID")
    public int groupID;
    @SerializedName("GroupName")
    public String groupName;
    @SerializedName("TerminalID")
    public int terminalID;
    @SerializedName("TerminalName")
    public String terminalName;
    @SerializedName("DeviceCode")
    public String deviceCode;
    @SerializedName("IsLock")
    public String isLock;
    @SerializedName("CreatedID")
    public int createdID;
    @SerializedName("CreatedDate")
    public String createdDate;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public int getCreatedID() {
        return createdID;
    }

    public void setCreatedID(int createdID) {
        this.createdID = createdID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
