package com.mbl.lottery.printer.loto.detail;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

import java.util.List;

public class DetailLotoInteractor  extends Interactor<DetailLotoContract.Presenter>
        implements DetailLotoContract.Interactor{
    public DetailLotoInteractor(DetailLotoContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getItemByCode(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getItemByOrderCode(code, callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImages(request, callback);
    }

    @Override
    public void updateImageKeno(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImageKeno(request, callback);
    }
}
