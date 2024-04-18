package com.mbl.lottery.printer.loto;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface PrinterLotoContract {
    interface Interactor extends IInteractor<PrinterLotoContract.Presenter> {
        void getOrder(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<PrinterLotoContract.Presenter> {
        void showOrder(List<OrderModel> orderModels);
    }

    interface Presenter extends IPresenter<PrinterLotoContract.View, PrinterLotoContract.Interactor> {
        void getOrder(int productID);
    }
}
