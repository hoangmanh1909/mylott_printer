package com.mbl.lottery.xskt.history;


import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.XSKTSearchTicketRequest;
import com.mbl.lottery.model.response.XSKTTickeResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class XSKTHistoryPresenter extends Presenter<XSKTHistoryContract.View, XSKTHistoryContract.Interactor>
        implements XSKTHistoryContract.Presenter {

    String status;

    public XSKTHistoryPresenter(ContainerView containerView, String status) {
        super(containerView);
        this.status = status;
    }

    @Override
    public void start() {
        getTicket();
    }

    @Override
    public XSKTHistoryContract.Interactor onCreateInteractor() {
        return new XSKTHistoryInteractor(this);
    }

    @Override
    public XSKTHistoryContract.View onCreateView() {
        return XSKTHistoryFragment.getInstance();
    }

    @Override
    public void getTicket() {
        mView.showProgress();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        XSKTSearchTicketRequest request = new XSKTSearchTicketRequest();
        request.setFromDate(formatter.format(yesterday));
        request.setToDate(formatter.format(date));
        request.setStatus(status);

        mInteractor.getTicket(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<XSKTTickeResponse> orders = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<XSKTTickeResponse>>() {
                    }.getType());
                    mView.showTicket(orders);
                } else {
                    mView.showTicket(new ArrayList<>());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showTicket(new ArrayList<>());
            }
        });
    }
}
