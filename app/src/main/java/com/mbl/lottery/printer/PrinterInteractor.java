package com.mbl.lottery.printer;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.DrawResponse;
import com.mbl.lottery.model.response.SearchOrderResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class PrinterInteractor  extends Interactor<PrinterContract.Presenter>
        implements PrinterContract.Interactor {

    public PrinterInteractor(PrinterContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void getKenoDraw(CommonCallback<SimpleResult> callback) {
        NetWorkController.getDrawKeno(callback);
    }

    @Override
    public void getOrder(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchOrder(searchOrderRequest, callback);
    }

    @Override
    public void getOrderKeno(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchOrderKeno(searchOrderRequest, callback);
    }

    @Override
    public void countOrderWattingPrint(int productID, int POSID, CommonCallback<BaseResponse> callback) {
        NetWorkController.countOrderWattingPrint(productID, POSID, callback);
    }

    @Override
    public void getDateTimeNow(CommonCallback<SimpleResult> callback) {
        NetWorkController.getDateTimeNow(callback);
    }

    @Override
    public void print(String orderCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.printByOrderCode(orderCode, callback);
    }

    @Override
    public void changeToPrinted(String orderCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.changeToPrinted(orderCode, callback);
    }
}
