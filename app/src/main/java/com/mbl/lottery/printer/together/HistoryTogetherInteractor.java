package com.mbl.lottery.printer.together;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.TogetherTicketSearchRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class HistoryTogetherInteractor extends Interactor<HistoryTogetherContract.Presenter>
        implements HistoryTogetherContract.Interactor {

    public HistoryTogetherInteractor(HistoryTogetherContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getTicket(TogetherTicketSearchRequest req, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchTogetherTicket(req, callback);
    }
}
