package com.mbl.lottery.printer.loto.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.printer.detail.DetailFragment;
import com.mbl.lottery.printer.detail.DetailInteractor;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DetailLotoPresenter  extends Presenter<DetailLotoContract.View, DetailLotoContract.Interactor>
        implements DetailLotoContract.Presenter {
    OrderModel orderModel;
    Activity activity;
    public DetailLotoPresenter(ContainerView containerView) {
        super(containerView);
        activity = (Activity) containerView;
    }

    @Override
    public void start() {
        Intent intent = activity.getIntent();
        orderModel = (OrderModel) intent.getSerializableExtra(Constants.ORDER_MODEL);
        getItemByCode(orderModel.getCode());
    }

    @Override
    public DetailLotoContract.Interactor onCreateInteractor() {
        return new DetailLotoInteractor(this);
    }

    @Override
    public DetailLotoContract.View onCreateView() {
        return DetailLotoFragment.getInstance();
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
    public void getItemByCode(String code) {
        mView.showProgress();
        mInteractor.getItemByCode(code, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<ItemModel> itemModelList = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ItemModel>>() {
                    }.getType());
                    mView.showItems(itemModelList);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void updateImages(List<OrderImagesRequest> request) {
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showOk();
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.updateImages(request, callback);
    }

    @Override
    public void updateImageKeno(OrderImagesRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showOk();
//                    Toast.showToast(getViewContext(), "Cập nhật thành công");
//                    new Handler().postDelayed(() -> {
//                        activity.finish();
//                    }, 2000);
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                Toast.showToast(getViewContext(), message);
            }
        };
        mInteractor.updateImageKeno(request, callback);
    }
}
