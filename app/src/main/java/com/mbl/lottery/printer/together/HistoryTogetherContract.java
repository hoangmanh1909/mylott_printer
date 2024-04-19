package com.mbl.lottery.printer.together;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.TogetherTicketSearchRequest;
import com.mbl.lottery.model.response.TogetherTicketSearchResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface HistoryTogetherContract {
    interface Interactor extends IInteractor<HistoryTogetherContract.Presenter> {
        void getTicket(TogetherTicketSearchRequest req, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<HistoryTogetherContract.Presenter> {
        void showTicket(List<TogetherTicketSearchResponse> models);
    }

    interface Presenter extends IPresenter<HistoryTogetherContract.View, HistoryTogetherContract.Interactor> {
        void getTicket(TogetherTicketSearchRequest req);
    }
}
