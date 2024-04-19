package com.mbl.lottery.printer.together;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.TogetherTicketSearchRequest;
import com.mbl.lottery.model.response.TogetherTicketSearchResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HistoryTogetherPresenter extends Presenter<HistoryTogetherContract.View, HistoryTogetherContract.Interactor>
        implements HistoryTogetherContract.Presenter {

    public HistoryTogetherPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public HistoryTogetherContract.Interactor onCreateInteractor() {
        return new HistoryTogetherInteractor(this);
    }

    @Override
    public HistoryTogetherContract.View onCreateView() {
        return HistoryTogetherFragment.getInstance();
    }

    @Override
    public void getTicket(TogetherTicketSearchRequest req) {
        mView.showProgress();
        mInteractor.getTicket(req, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<TogetherTicketSearchResponse> orders = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<TogetherTicketSearchResponse>>() {
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
