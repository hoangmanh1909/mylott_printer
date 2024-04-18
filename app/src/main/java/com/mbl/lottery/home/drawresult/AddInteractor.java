package com.mbl.lottery.home.drawresult;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.AddDrawResultRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class AddInteractor  extends Interactor<AddContract.Presenter>
        implements AddContract.Interactor {

    public AddInteractor(AddContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void resultAdd(AddDrawResultRequest req, CommonCallback<SimpleResult> callback) {
        NetWorkController.addDrawResult(req,callback);
    }
}
