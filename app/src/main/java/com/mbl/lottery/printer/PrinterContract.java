package com.mbl.lottery.printer;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.DrawResponse;
import com.mbl.lottery.model.response.PrintResponse;
import com.mbl.lottery.model.response.SearchOrderResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface PrinterContract {
    interface Interactor extends IInteractor<PrinterContract.Presenter> {
        void getKenoDraw(CommonCallback<SimpleResult> callback);
        void getOrder(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback);
        void getOrderKeno(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback);
        void countOrderWattingPrint(int productID, int POSID, CommonCallback<BaseResponse> callback);
        void getDateTimeNow(CommonCallback<SimpleResult> callback);
        void print(String orderCode, CommonCallback<SimpleResult> callback);
        void changeToPrinted(String orderCode, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<PrinterContract.Presenter> {
        void showTimeNow(String timeNow);
        void showOrder(List<OrderModel> orderModels);
        void showKenoDraw(List<DrawModel> drawModels);
        void showPrint(String code, PrintCommandModel printCommandModel);
        void showCountWaitPrint(int value);
        void showPrintSuccess(String code);
    }

    interface Presenter extends IPresenter<PrinterContract.View, PrinterContract.Interactor> {
        void getOrder(int productID,int type,String status);
        void print(String orderCode);
        void countOrderWattingPrint(int productID);
        void getDateTimeNow();
        void getKenoDraw();
        void changeToPrinted(String orderCode);
    }
}
