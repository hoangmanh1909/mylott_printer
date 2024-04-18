package com.mbl.lottery.printer.loto;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.printer.PrinterContract;

public class PrinterLotoInteractor  extends Interactor<PrinterLotoContract.Presenter>
        implements PrinterLotoContract.Interactor {
    public PrinterLotoInteractor(PrinterLotoContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getOrder(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchOrderLoto(searchOrderRequest, callback);
    }
}
