package com.mbl.lottery.xskt.detail;


import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class XSKTDetailInteractor  extends Interactor<XSKTDetailContract.Presenter>
        implements XSKTDetailContract.Interactor{
    public XSKTDetailInteractor(XSKTDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getItemByCode(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getItemXsktByOrderCode(code, callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateImageKeno(request, callback);
    }
}
