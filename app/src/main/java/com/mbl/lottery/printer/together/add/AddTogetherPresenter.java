package com.mbl.lottery.printer.together.add;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.TogetherTicketAddRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddTogetherPresenter extends Presenter<AddTogetherContract.View, AddTogetherContract.Interactor>
        implements AddTogetherContract.Presenter{

    public AddTogetherPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public AddTogetherContract.Interactor onCreateInteractor() {
        return new AddTogetherInteractor(this);
    }

    @Override
    public AddTogetherContract.View onCreateView() {
        return AddTogetherFragment.getInstance();
    }

    @Override
    public void addTogether(TogetherTicketAddRequest req) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    Toast.showToast(getViewContext(), "Cập nhật thành công");
                    back();
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.addTogether(req, callback);
    }

    @Override
    public void postImage(String filePath) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    FileInfoModel fileInfoModel = NetWorkController.getGson().fromJson(response.body().getData(), FileInfoModel.class);
                    mView.showImage(fileInfoModel.getFileName());
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.postImage(filePath, callback);
    }

    @Override
    public void getDrawMega() {
        mInteractor.getDrawMega( new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    List<DrawModel> drawModels = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<DrawModel>>() {
                    }.getType());
                    mView.showDraw(drawModels);
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void getDrawPower() {
        mInteractor.getDrawPower( new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    List<DrawModel> drawModels = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<DrawModel>>() {
                    }.getType());
                    mView.showDraw(drawModels);
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }
}
