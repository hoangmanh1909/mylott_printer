package com.mbl.lottery.login;

import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.LoginRequest;
import com.mbl.lottery.model.response.LoginResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    public LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void login(LoginRequest loginRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.login(loginRequest, callback);
    }
}
