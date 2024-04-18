package com.mbl.lottery.model.response;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.SimpleResult;

import java.util.List;

@SuppressLint("ParcelCreator")
public class UploadResponse extends SimpleResult {
    @SerializedName("ListValue")
    private List<FileInfoModel> fileInfoModels;

    public List<FileInfoModel> getFileInfoModels() {
        return fileInfoModels;
    }
}
