package com.mbl.lottery.xskt.detail;


import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.response.GetItemXsktResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class XSKTDetailPresenter  extends Presenter<XSKTDetailContract.View, XSKTDetailContract.Interactor>
        implements XSKTDetailContract.Presenter {

    OrderModel orderModel;
    Activity activity;
    public XSKTDetailPresenter(ContainerView containerView, OrderModel orderModel) {
        super(containerView);
        activity = (Activity) containerView;
        this.orderModel = orderModel;
    }

    @Override
    public void start() {
        getItemByCode(orderModel.getCode());
    }

    @Override
    public XSKTDetailContract.Interactor onCreateInteractor() {
        return new XSKTDetailInteractor(this);
    }

    @Override
    public XSKTDetailContract.View onCreateView() {
        return XSKTDetailFragment.getInstance();
    }
    @Override
    public OrderModel getOrderModel() {
        return orderModel;
    }
    @Override
    public void getItemByCode(String code) {
        mView.showProgress();
        mInteractor.getItemByCode(orderModel.getCode(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<GetItemXsktResponse> items = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<GetItemXsktResponse>>() {
                    }.getType());
                    mView.showItem(items);
                } else {
                    mView.showItem(new ArrayList<>());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showItem(new ArrayList<>());
            }
        });
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
    public void updateImage(OrderImagesRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    Toast.showToast(getViewContext(), "Cập nhật thành công");
                    back();
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
        mInteractor.updateImage(request, callback);
    }
}
