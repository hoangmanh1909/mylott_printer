package com.mbl.lottery.printer;

import android.app.Activity;
import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.DateTimeServer;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.DrawResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DateTimeUtils;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PrinterPresenter extends Presenter<PrinterContract.View, PrinterContract.Interactor>
        implements PrinterContract.Presenter {

    SharedPref sharedPref;
    EmployeeModel employeeModel;
    int mProductID;

    public PrinterPresenter(ContainerView containerView) {
        super(containerView);

        Intent intent = ((Activity) mContainerView).getIntent();
        sharedPref = new SharedPref((Activity) mContainerView);

        employeeModel = sharedPref.getEmployeeModel();
        mProductID = intent.getIntExtra(Constants.PRODUCT_ID, 0);
    }

    @Override
    public void getOrder(int productID,int type,String status) {
        mView.showProgress();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        Date date = new Date();

        SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
        searchOrderRequest.setTerminalID(employeeModel.getTerminalID());
        searchOrderRequest.setProductID(productID);
        searchOrderRequest.setType(type);
        searchOrderRequest.setFromDate(formatter.format(yesterday));
        searchOrderRequest.setToDate(formatter.format(date));
        searchOrderRequest.setStatus(status);

        if(productID == Constants.PRODUCT_KENO) {
            mInteractor.getOrderKeno(searchOrderRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
        }else{
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

    @Override
    public void print(String orderCode) {
        mInteractor.print(orderCode,new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    PrintCommandModel printCommandModel = NetWorkController.getGson().fromJson(response.body().getData(), PrintCommandModel.class);
                    mView.showPrint(orderCode, printCommandModel);
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                Toast.showToast(getViewContext(), message);
            }
        });
    }

    @Override
    public void countOrderWattingPrint(int productID) {
        mInteractor.countOrderWattingPrint(productID,employeeModel.getTerminalID(),new CommonCallback<BaseResponse>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<BaseResponse> call, Response<BaseResponse> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    int countWait = (int)Float.parseFloat(response.body().getValue().toString());
                    mView.showCountWaitPrint(countWait);
                }
            }

            @Override
            protected void onError(Call<BaseResponse> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void start() {
        if (mProductID == Constants.PRODUCT_KENO) {
            getDateTimeNow();
        }
    }

    @Override
    public void getDateTimeNow(){
        mInteractor.getDateTimeNow(new CommonCallback<SimpleResult>((Activity)mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    DateTimeServer dateTimeServer = NetWorkController.getGson().fromJson(response.body().getData(), DateTimeServer.class);
                    mView.showTimeNow(dateTimeServer.getDateTimeNow());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void getKenoDraw() {
        mInteractor.getKenoDraw(new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<DrawModel> drawModels = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<DrawModel>>() {
                    }.getType());
                    mView.showKenoDraw(drawModels);
                } else {
                    mView.showOrder(new ArrayList<>());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void changeToPrinted(String orderCode) {
        mInteractor.changeToPrinted(orderCode,new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showPrintSuccess(orderCode);
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                Toast.showToast(getViewContext(), message);
            }
        });
    }

    @Override
    public PrinterContract.Interactor onCreateInteractor() {
        return new PrinterInteractor(this);
    }

    @Override
    public PrinterContract.View onCreateView() {
        return PrinterFragment.getInstance();
    }
}
