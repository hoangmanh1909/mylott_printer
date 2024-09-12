package com.mbl.lottery.xskt.history;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.XSKTSearchTicketRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class XSKTHistoryInteractor  extends Interactor<XSKTHistoryContract.Presenter>
        implements XSKTHistoryContract.Interactor {

    public XSKTHistoryInteractor(XSKTHistoryContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void getTicket(XSKTSearchTicketRequest req, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchXsktTicket(req,callback);
    }
}