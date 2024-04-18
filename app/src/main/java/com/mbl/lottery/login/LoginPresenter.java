package com.mbl.lottery.login;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.LoginRequest;
import com.mbl.lottery.model.response.LoginResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenter extends Presenter<LoginContract.View, LoginContract.Interactor>
        implements LoginContract.Presenter {

    public LoginPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public LoginContract.Interactor onCreateInteractor() {
        return new LoginInteractor(this);
    }

    @Override
    public LoginContract.View onCreateView() {
        return LoginFragment.getInstance();
    }

    @Override
    public void login(String phone, String passWord) {
        mView.showProgress();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(phone);
        loginRequest.setPassword(passWord);
        mInteractor.login(loginRequest,new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())){
                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    EmployeeModel accountInfo = NetWorkController.getGson().fromJson(response.body().getData(), EmployeeModel.class);
                    sharedPref.saveEmployee(accountInfo);
                    mView.goHome();
                }
                else {
                    mView.showAlertDialog(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }
}
