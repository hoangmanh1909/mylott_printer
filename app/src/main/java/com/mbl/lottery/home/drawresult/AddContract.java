package com.mbl.lottery.home.drawresult;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.model.request.AddDrawResultRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.printer.topup.TopupContract;

public interface AddContract {
    interface Interactor extends IInteractor<AddContract.Presenter> {
        void  resultAdd(AddDrawResultRequest req, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<AddContract.Presenter> {
        void showSuccess();
    }

    interface Presenter extends IPresenter<AddContract.View, AddContract.Interactor> {
        void  resultAdd(AddDrawResultRequest req);
        int getProduct();
    }
}
