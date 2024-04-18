package com.mbl.lottery.printer.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.DateTimeServer;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.FinishOrderKenoRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DetailPresenter extends Presenter<DetailContract.View, DetailContract.Interactor>
        implements DetailContract.Presenter {

    OrderModel orderModel;
    List<DrawModel> drawModels;
    Activity activity;

//    public DetailPresenter(ContainerView containerView, OrderModel orderModel, List<DrawModel> drawModels) {
//        super(containerView);
//
//        this.orderModel = orderModel;
//        this.drawModels = drawModels;
//    }

    public DetailPresenter(ContainerView containerView) {
        super(containerView);
        activity = (Activity) containerView;
//        this.orderModel = orderModel;
//        this.drawModels = drawModels;
    }

    @Override
    public void start() {
        Intent intent = activity.getIntent();
        drawModels = (List<DrawModel>) intent.getSerializableExtra(Constants.DRAW_MODEL);
        orderModel = (OrderModel) intent.getSerializableExtra(Constants.ORDER_MODEL);
        if (orderModel.getProductID() == Constants.PRODUCT_KENO)
            getDateTimeNow();
        else
            getItemByCode();
    }

    @Override
    public void getDateTimeNow() {
        mInteractor.getDateTimeNow(new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    DateTimeServer dateTimeServer = NetWorkController.getGson().fromJson(response.body().getData(), DateTimeServer.class);
                    mView.showTimeNow(dateTimeServer.getDateTimeNow());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void getItemByCode() {
        mView.showProgress();
        mInteractor.getItemByCode(orderModel.getCode(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<ItemModel> items = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ItemModel>>() {
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
    public OrderModel getOrderModel() {
        return orderModel;
    }

    @Override
    public List<DrawModel> getDrawModels() {
        return drawModels;
    }

    @Override
    public void print(List<ItemModel> models) {
        mView.showProgress();

        mInteractor.print(models, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    PrintCommandModel printCommandModel = NetWorkController.getGson().fromJson(response.body().getData(), PrintCommandModel.class);
                    mView.showPrint(models.get(0).getOrderCode(), printCommandModel);
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void postImage(String filePath, String type) {
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
    public void finishOrderKeno(FinishOrderKenoRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    Toast.showToast(getViewContext(), "Cập nhật thành công");
                    new Handler().postDelayed(() -> {
                        activity.finish();
                    }, 2000);
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
        mInteractor.finishOrderKeno(request, callback);
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
        mInteractor.updateImage(request, callback);
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

    @Override
    public void changeToImage(String code) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showSuccessImage();
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        ChangeUpImageRequest request = new ChangeUpImageRequest();
        request.setCode(code);
        mInteractor.changeToImage(request, callback);
    }


    @Override
    public DetailContract.Interactor onCreateInteractor() {
        return new DetailInteractor(this);
    }

    @Override
    public DetailContract.View onCreateView() {
        return DetailFragment.getInstance();
    }
}
