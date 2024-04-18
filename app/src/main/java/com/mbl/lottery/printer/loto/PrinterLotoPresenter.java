package com.mbl.lottery.printer.loto;

import android.app.Activity;
import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.printer.PrinterContract;
import com.mbl.lottery.printer.PrinterFragment;
import com.mbl.lottery.printer.PrinterInteractor;
import com.mbl.lottery.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PrinterLotoPresenter  extends Presenter<PrinterLotoContract.View, PrinterLotoContract.Interactor>
        implements PrinterLotoContract.Presenter {

    SharedPref sharedPref;
    EmployeeModel employeeModel;
    public PrinterLotoPresenter(ContainerView containerView) {
        super(containerView);

        Intent intent = ((Activity) mContainerView).getIntent();
        sharedPref = new SharedPref((Activity) mContainerView);

        employeeModel = sharedPref.getEmployeeModel();
    }

    @Override
    public void start() {
       getOrder(0);
    }

    @Override
    public PrinterLotoContract.Interactor onCreateInteractor() {
        return new PrinterLotoInteractor(this);
    }

    @Override
    public PrinterLotoContract.View onCreateView() {
        return PrinterLotoFragment.getInstance();
    }

    @Override
    public void getOrder(int productID) {
        SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
        searchOrderRequest.setProductID(productID);
        searchOrderRequest.setTerminalID(employeeModel.terminalID);
        mInteractor.getOrder(searchOrderRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<OrderModel> orders = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<OrderModel>>() {
                    }.getType());
                    mView.showOrder(orders);
                } else {
                    mView.showOrder(new ArrayList<>());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showOrder(new ArrayList<>());
            }
        });
    }
}
