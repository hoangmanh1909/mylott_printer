package com.mbl.lottery.home;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.ParamsResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor{

    public HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getDateTimeNow(CommonCallback<SimpleResult> callback) {
        NetWorkController.getDateTimeNow(callback);
    }

    @Override
    public void getParams(CommonCallback<ParamsResponse> callback) {
        NetWorkController.getAllConfig(callback);
    }
}
