package com.mbl.lottery.printer.upload;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.request.OrderTablesImagesRequest;
import com.mbl.lottery.model.response.UploadResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.printer.detail.DetailActivity;
import com.mbl.lottery.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.blankj.utilcode.util.ActivityUtils.finishActivity;
import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class UploadPresenter extends Presenter<UploadContract.View, UploadContract.Interactor>
        implements UploadContract.Presenter {

    OrderModel orderModel;
    List<ItemModel> itemModelList;

    public UploadPresenter(ContainerView containerView, OrderModel orderModel, List<ItemModel> itemModels) {
        super(containerView);

        this.orderModel = orderModel;
        this.itemModelList = itemModels;
    }

    @Override
    public void start() {
        if(itemModelList.size() == 0)
            getItemByCode(orderModel.getCode());
    }

    @Override
    public UploadContract.Interactor onCreateInteractor() {
        return new UploadInteractor(this);
    }

    @Override
    public UploadContract.View onCreateView() {
        return UploadFragment.getInstance();
    }

    @Override
    public OrderModel getOrderModel() {
        return orderModel;
    }

    @Override
    public List<ItemModel> getItemModels() {
        return itemModelList;
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
                }
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
    public void print(String orderCode) {
        mInteractor.print(orderCode,new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    PrintCommandModel printCommandModel = NetWorkController.getGson().fromJson(response.body().getData(), PrintCommandModel.class);
                    mView.showPrint(orderCode, printCommandModel);
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                Toast.showToast(getViewContext(), message);
            }
        });
    }

    @Override
    public void getItemByCode(String code) {
        mView.showProgress();
        mInteractor.getItemByCode(code, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    itemModelList = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ItemModel>>() {
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
    public void changeToImage(String code) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showChangeToImage();
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
    public void updateImage(OrderImagesRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    Toast.showToast(getViewContext(), "Cập nhật thành công");

                    new Handler().postDelayed(() -> {
                        finishActivity(DetailActivity.class);
                    }, 2000);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.updateImage(request, callback);
    }

    @Override
    public void updateImages(List<OrderImagesRequest> request) {
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
//                    Toast.showToast(getViewContext(), "Cập nhật thành công");
                    mView.showOkSuccess();
//                    back();
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
    public void changeToPrinted(String orderCode) {
        mInteractor.changeToPrinted(orderCode,new CommonCallback<SimpleResult>((Activity)mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showPrintSuccess(orderCode);
                } else {
                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                Toast.showToast(getViewContext(), message);
            }
        });
    }

    @Override
    public void updateImageV1(OrderTablesImagesRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    Toast.showToast(getViewContext(), "Cập nhật thành công");

                    new Handler().postDelayed(() -> {
                        finishActivity(DetailActivity.class);
                    }, 2000);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.updateImageV1(request, callback);
    }
}
