package com.mbl.lottery.home;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.ParamsResponse;
import com.mbl.lottery.network.CommonCallback;

public interface HomeContract {
    interface Interactor extends IInteractor<HomeContract.Presenter> {
        void getDateTimeNow(CommonCallback<SimpleResult> callback);

        void getParams(CommonCallback<ParamsResponse> callback);
    }

    interface View extends PresentView<HomeContract.Presenter> {
    }

    interface Presenter extends IPresenter<HomeContract.View, HomeContract.Interactor> {

    }
}
