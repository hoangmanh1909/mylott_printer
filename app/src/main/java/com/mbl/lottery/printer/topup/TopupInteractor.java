package com.mbl.lottery.printer.topup;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class TopupInteractor  extends Interactor<TopupContract.Presenter>
        implements TopupContract.Interactor {
    public TopupInteractor(TopupContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void topupAdd(TopupAddRequest req, CommonCallback<SimpleResult> callback) {
        NetWorkController.topupAdd(req, callback);
    }
}
