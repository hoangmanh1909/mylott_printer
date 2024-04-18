package com.mbl.lottery.login;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.LoginRequest;
import com.mbl.lottery.model.response.LoginResponse;
import com.mbl.lottery.network.CommonCallback;

public interface LoginContract {
    interface Interactor extends IInteractor<Presenter> {
        void login(LoginRequest loginRequest, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void goHome();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void login(String phone, String passWord);
    }
}
