package com.mbl.lottery.together;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

public interface TogetherContract {
    interface Interactor extends IInteractor<TogetherContract.Presenter> {
    }

    interface View extends PresentView<TogetherContract.Presenter> {
    }

    interface Presenter extends IPresenter<TogetherContract.View, TogetherContract.Interactor> {

    }
}
