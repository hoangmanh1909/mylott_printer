package com.mbl.lottery.printer.together.add;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.TogetherTicketAddRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class AddTogetherInteractor extends Interactor<AddTogetherContract.Presenter>
        implements AddTogetherContract.Interactor {

    public AddTogetherInteractor(AddTogetherContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void addTogether(TogetherTicketAddRequest req, CommonCallback<SimpleResult> callback) {
        NetWorkController.addTogetherTicket(req, callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void getDrawMega(CommonCallback<SimpleResult> callback) {
        NetWorkController.getDrawMega(callback);
    }

    @Override
    public void getDrawPower(CommonCallback<SimpleResult> callback) {
        NetWorkController.getDrawPower(callback);
    }
}
