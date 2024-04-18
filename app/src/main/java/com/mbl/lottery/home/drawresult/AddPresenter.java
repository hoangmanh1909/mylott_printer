package com.mbl.lottery.home.drawresult;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.login.LoginInteractor;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.AddDrawResultRequest;
import com.mbl.lottery.model.request.LoginRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

public class AddPresenter extends Presenter<AddContract.View, AddContract.Interactor>
        implements AddContract.Presenter {

    int product = 1;
    public AddPresenter(ContainerView containerView,int _product) {
        super(containerView);
        this.product = _product;
    }
    @Override
    public int getProduct(){
        return  product;
    }
    @Override
    public void start() {

    }

    @Override
    public AddContract.Interactor onCreateInteractor() {
        return new AddInteractor(this);
    }

    @Override
    public AddContract.View onCreateView() {
        return AddFragment.getInstance();
    }

    @Override
    public void resultAdd(AddDrawResultRequest req) {
        mView.showProgress();
        mInteractor.resultAdd(req, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                mView.showAlertDialog(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }
}
