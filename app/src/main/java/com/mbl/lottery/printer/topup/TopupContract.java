package com.mbl.lottery.printer.topup;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.network.CommonCallback;

public interface TopupContract {
    interface Interactor extends IInteractor<TopupContract.Presenter> {
        void  topupAdd(TopupAddRequest req, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<TopupContract.Presenter> {
        void showSuccess();
    }

    interface Presenter extends IPresenter<TopupContract.View, TopupContract.Interactor> {
        void  topupAdd(TopupAddRequest req);
    }
}
