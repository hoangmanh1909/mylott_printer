package com.mbl.lottery.printer.detail.outOfNumber;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OutOfNumberRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class OutOfNumberInteractor  extends Interactor<OutOfNumberContract.Presenter>
        implements OutOfNumberContract.Interactor {

    public OutOfNumberInteractor(OutOfNumberContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void outOfNumber(OutOfNumberRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.outOfNumber(request, callback);
    }
}
