package com.mbl.lottery.printer.detail;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.FinishOrderKenoRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.GetItemByCodeResponse;
import com.mbl.lottery.model.response.PrintResponse;
import com.mbl.lottery.model.response.UploadResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

import java.util.List;

public class DetailInteractor extends Interactor<DetailContract.Presenter>
        implements DetailContract.Interactor {

    public DetailInteractor(DetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getDateTimeNow(CommonCallback<SimpleResult> callback) {
        NetWorkController.getDateTimeNow(callback);
    }

    @Override
    public void getItemByCode(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getItemByOrderCode(code, callback);
    }

    @Override
    public void print(List<ItemModel> itemModels, CommonCallback<SimpleResult> callback) {
        NetWorkController.print(itemModels, callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void finishOrderKeno(FinishOrderKenoRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.finsishOrderKeno(request, callback);
    }

    @Override
    public void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImage(request, callback);
    }

    @Override
    public void updateImageKeno(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImageKeno(request, callback);
    }

    @Override
    public void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImages(request, callback);
    }

    @Override
    public void changeToImage(ChangeUpImageRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.changeToImage(request, callback);
    }
}
