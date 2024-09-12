package com.mbl.lottery.xskt.history;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.XSKTSearchTicketRequest;
import com.mbl.lottery.model.response.XSKTTickeResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface XSKTHistoryContract {
    interface Interactor extends IInteractor<Presenter> {
        void getTicket(XSKTSearchTicketRequest req, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showTicket(List<XSKTTickeResponse> models);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getTicket();
    }
}
