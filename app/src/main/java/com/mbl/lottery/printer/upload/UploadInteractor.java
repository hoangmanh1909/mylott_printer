package com.mbl.lottery.printer.upload;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.request.OrderTablesImagesRequest;
import com.mbl.lottery.model.response.UploadResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

import java.util.List;

public class UploadInteractor extends Interactor<UploadContract.Presenter>
        implements UploadContract.Interactor {

    public UploadInteractor(UploadContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void print(String orderCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.printByOrderCode(orderCode, callback);
    }

    @Override
    public void changeToPrinted(String orderCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.changeToPrinted(orderCode, callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void getItemByCode(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getItemByOrderCode(code, callback);
    }

    @Override
    public void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImages(request, callback);
    }

    @Override
    public void updateImageV1(OrderTablesImagesRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImageV1(request, callback);
    }

    @Override
    public void changeToImage(ChangeUpImageRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.changeToImage(request, callback);
    }

    @Override
    public void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {

    }

}
