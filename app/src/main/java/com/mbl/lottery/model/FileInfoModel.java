package com.mbl.lottery.model;

import com.google.gson.annotations.SerializedName;

public class FileInfoModel {
    @SerializedName("FileName")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
