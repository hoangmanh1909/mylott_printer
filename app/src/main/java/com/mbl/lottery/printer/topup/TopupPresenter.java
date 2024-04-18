package com.mbl.lottery.printer.topup;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.main.MainFragment;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.Toast;

import retrofit2.Call;
import retrofit2.Response;

public class TopupPresenter   extends Presenter<TopupContract.View, TopupContract.Interactor>
        implements TopupContract.Presenter{
    public TopupPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TopupContract.Interactor onCreateInteractor() {
        return new TopupInteractor(this);
    }

    @Override
    public TopupContract.View onCreateView() {
        return TopupFragment.getInstance();
    }

    @Override
    public void topupAdd(TopupAddRequest req) {
        mView.showProgress();
        mInteractor.topupAdd(req,new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showSuccess();
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
                mView.hideProgress();
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);mView.hideProgress();
                Toast.showToast(getViewContext(), message);
            }
        });
    }
}
